package com.durdinstudios.goonwarscollector.domain.wallet

import com.durdinstudios.goonwarscollector.core.BootstrapAction
import com.durdinstudios.goonwarscollector.core.arch.BaseSaga
import com.durdinstudios.goonwarscollector.core.arch.Resource
import com.durdinstudios.goonwarscollector.core.arch.createAppStateSelector
import com.durdinstudios.goonwarscollector.core.arch.getResourceValue
import com.durdinstudios.goonwarscollector.core.arch.onFailure
import com.durdinstudios.goonwarscollector.core.arch.onSuccess
import com.durdinstudios.goonwarscollector.domain.Article
import com.durdinstudios.goonwarscollector.domain.persistence.PersistenceController
import com.durdinstudios.goonwarscollector.domain.persistence.settings.Keys
import com.durdinstudios.goonwarscollector.ui.collection.CollectionCardItem
import com.minikorp.duo.ActionContext
import com.minikorp.duo.Reducer
import com.minikorp.duo.State
import com.minikorp.duo.TypedReducer
import org.kodein.di.DI
import org.kodein.di.instance
import java.io.Serializable

val statsSelector = createAppStateSelector(p1 = { it.wallet }, selector = { it.stats })
val cardsSelector = createAppStateSelector(p1 = { it.wallet }, selector = { it.gobCards })
val userCardsSelector = createAppStateSelector(p1 = { it.wallet }, selector = {
    val gobCardsResource = it.gobCards
    val userCardsResource = it.userCards
    when {
        gobCardsResource.isSuccess && userCardsResource.isSuccess -> {
            val gobCards = gobCardsResource.getResourceValue()!!
            val userCards = userCardsResource.getResourceValue()!!
            Resource.success(gobCards.map { card ->
                val ownership = userCards.firstOrNull { it.id == card.id }
                val amount = ownership?.ownedRegular ?: if (card.id.startsWith("B")) -1 else 0
                val shinyAmount = ownership?.ownedShiny ?: if (card.id.startsWith("B")) -1 else 0
                CollectionCardItem(card, amount, shinyAmount)
            }.sortedBy { it.card.name })
        }

        gobCardsResource.isFailure || userCardsResource.isFailure -> Resource.failure(gobCardsResource.exceptionOrNull() ?: userCardsResource.exceptionOrNull())
        gobCardsResource.isLoading || userCardsResource.isLoading -> Resource.loading()
        gobCardsResource.isEmpty || userCardsResource.isEmpty -> Resource.empty()
        else -> Resource.idle()
    }
})

val articlesSelector = createAppStateSelector(p1 = { it.wallet }, selector = { it.articles })
val userWallets = createAppStateSelector(p1 = { it.wallet }, selector = { it.walletAddresses })

@State
data class WalletState(
    val walletAddresses: List<String> = emptyList(),
    val articles: Resource<List<Article>> = Resource.empty(),
    val stats: Resource<MarketStats> = Resource.empty(),
    val gobCards: Resource<List<GobCard>> = Resource.empty(),
    val userCards: Resource<List<CardOwnership>> = Resource.empty()
) : Serializable

@TypedReducer
class WalletSaga(di: DI) : BaseSaga<WalletState>(di) {
    private val controller: WalletController by instance()
    private val persistenceController: PersistenceController by instance()

    @TypedReducer.Fun
    suspend fun bootstrap(ctx: ActionContext<WalletState>, action: BootstrapAction) {
        val storedWallets = persistenceController.getListOfValues(Keys.UserWallets(), emptyList<String>())
        ctx.reduce { it.copy(walletAddresses = storedWallets) }
        ctx.dispatch(GetGobCardsAction.Request)
    }

    @TypedReducer.Fun
    suspend fun addLocalWallet(ctx: ActionContext<WalletState>, action: TrackWalletAction.Request) {
        persistenceController.setValue(Keys.UserWallets(), action.wallets)
        ctx.reduce { it.copy(walletAddresses = action.wallets) }
    }

    @TypedReducer.Fun
    suspend fun getCards(ctx: ActionContext<WalletState>, action: GetGobCardsAction.Request) {
        ctx.reduce { it.copy(gobCards = Resource.loading()) }
        controller.getGobCards()
            .onSuccess { ctx.dispatch(GetGobCardsAction.Response(Resource.success(it))) }
            .onFailure { ctx.dispatch(GetGobCardsAction.Response(Resource.failure(it))) }
    }

    @TypedReducer.Fun
    suspend fun getUserCards(ctx: ActionContext<WalletState>, action: GetUserGobCardsAction.Request) {
        ctx.reduce { it.copy(userCards = Resource.loading()) }
        controller.getWalletCardsOwnership(action.wallet)
            .onSuccess { ctx.dispatch(GetUserGobCardsAction.Response(Resource.success(it))) }
            .onFailure { ctx.dispatch(GetUserGobCardsAction.Response(Resource.failure(it))) }
    }

    @TypedReducer.Fun
    suspend fun getArticles(ctx: ActionContext<WalletState>, action: GetGobArticles.Request) {
        ctx.reduce { it.copy(articles = Resource.loading()) }
        controller.getGobArticles()
            .onSuccess { ctx.dispatch(GetGobArticles.Response(Resource.success(it))) }
            .onFailure { ctx.dispatch(GetGobArticles.Response(Resource.failure(it))) }
    }

    @TypedReducer.Fun
    suspend fun getStats(ctx: ActionContext<WalletState>, action: GetMarketStats.Request) {
        ctx.reduce { it.copy(stats = Resource.loading()) }
        controller.getStats()
            .onSuccess { ctx.dispatch(GetMarketStats.Response(Resource.success(it))) }
            .onFailure { ctx.dispatch(GetMarketStats.Response(Resource.failure(it))) }
    }

    @TypedReducer.Root
    override suspend fun reduce(ctx: ActionContext<WalletState>) {
        reduceTyped(ctx)
    }

}

@TypedReducer
class WalletReducer : Reducer<WalletState> {

    @TypedReducer.Fun
    fun handleGetCards(
        state: WalletState,
        action: GetGobCardsAction.Response
    ): WalletState {
        return state.copy(gobCards = action.cards)
    }

    @TypedReducer.Fun
    fun handleGetUserCards(
        state: WalletState,
        action: GetUserGobCardsAction.Response
    ): WalletState {
        return state.copy(userCards = action.cards)
    }

    @TypedReducer.Fun
    fun handleGetStats(
        state: WalletState,
        action: GetMarketStats.Response
    ): WalletState {
        return state.copy(stats = action.stats)
    }

    @TypedReducer.Fun
    fun handleGetArticles(
        state: WalletState,
        action: GetGobArticles.Response
    ): WalletState {
        return state.copy(articles = action.articles)
    }

    @TypedReducer.Root
    override suspend fun reduce(ctx: ActionContext<WalletState>) {
        ctx.mutableState = reduceTyped(ctx) ?: ctx.state
    }
}