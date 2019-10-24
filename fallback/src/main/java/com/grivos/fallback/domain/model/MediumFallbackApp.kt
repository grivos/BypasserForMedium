package com.grivos.fallback.domain.model

sealed class MediumFallbackApp
object MediumFallbackAppNone : MediumFallbackApp()
class MediumFallbackAppExists(val app: InstalledAppInfo) : MediumFallbackApp()
