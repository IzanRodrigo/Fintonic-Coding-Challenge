package com.izanrodrigo.fintonictestchallenge.util.extensions

import android.view.View

inline val View.transitionPair
    get() = this to transitionName
