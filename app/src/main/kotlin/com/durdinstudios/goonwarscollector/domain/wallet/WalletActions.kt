package com.durdinstudios.goonwarscollector.domain.wallet

import com.durdinstudios.goonwarscollector.core.arch.Resource
import com.durdinstudios.goonwarscollector.core.arch.RootLogAction
import com.durdinstudios.goonwarscollector.domain.Article
import com.minikorp.duo.Action
import com.minikorp.duo.TypedAction

interface GetGobCardsAction {
    @TypedAction
    object Request : Action, RootLogAction

    @TypedAction
    data class Response(val cards: Resource<List<GobCard>>) : Action
}

interface TrackWalletAction {
    @TypedAction
    data class  Request (val wallets: List<String>): Action, RootLogAction

    @TypedAction
    data class Response(val cards: Resource<List<GobCard>>) : Action
}

interface GetGobArticles {
    @TypedAction
    object Request : Action, RootLogAction

    @TypedAction
    data class Response(val articles: Resource<List<Article>>) : Action
}

interface GetMarketStats {
    @TypedAction
    object Request : Action, RootLogAction

    @TypedAction
    data class Response(val stats: Resource<MarketStats>) : Action
}


interface GetUserGobCardsAction {
    @TypedAction
    data class Request(val wallet: List<String>) : Action, RootLogAction

    @TypedAction
    data class Response(val cards: Resource<List<CardOwnership>>) : Action
}
