package com.grivos.fallback.domain.model

sealed class MediumInterceptionResult
object MediumInterceptionResultPremium : MediumInterceptionResult()
class MediumInterceptionResultNotPremium(val fallbackApp: MediumFallbackApp) :
    MediumInterceptionResult()
