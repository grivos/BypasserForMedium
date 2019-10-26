package com.grivos.fallback.domain.model

sealed class MediumInterceptionResult
class MediumInterceptionResultPremium(val url: String) : MediumInterceptionResult()
class MediumInterceptionResultNotPremium(val fallbackApp: MediumFallbackApp) :
    MediumInterceptionResult()
