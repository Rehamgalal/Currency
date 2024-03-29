package com.scan.cureencybase.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class BaseResponse(var message: String? = null, var code: String? = null) : Parcelable
