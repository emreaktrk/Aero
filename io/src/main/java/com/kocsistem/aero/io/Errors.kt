package com.kocsistem.aero.io

import com.android.volley.VolleyError

open class NullResponseError : VolleyError("Nothing to parse because response network has no response")

open class NullDataError : VolleyError("Body is null")

open class BodyParseError : VolleyError("Body does not have a valid json format or type does not match with response")

open class CharsetError : VolleyError("There is charset error")

open class ContextError : VolleyError("Context is null, Unable to queue. Call with IO#create(context)")