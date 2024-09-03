package dev.bondar.nbp_main

import kotlinx.collections.immutable.ImmutableList

public sealed class State(public open val rates: ImmutableList<RateUI>?) {

    public data object None : State(rates = null )

    public class Loading(rates: ImmutableList<RateUI>? = null) : State(rates)

    public class Error(rates: ImmutableList<RateUI>? = null) : State(rates)

    public class Success(override val rates: ImmutableList<RateUI>) : State(rates)
}