import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import com.blankj.utilcode.util.SizeUtils

class StyleColorUtils {
    companion object {
        var BASE_COLOR_PURPLE_ONE_NORMAL: Int = 0xffA093EC.toInt()
        var BASE_COLOR_PURPLE_ONE_PRESSED: Int = 0xff8A7FCD.toInt()
        var BASE_COLOR_WHITE_ONE: Int = 0xffe8f0f1.toInt()
        var BASE_COLOR_GREEN_TWO_NORMAL: Int = 0xff6DDACB.toInt()
        var BASE_COLOR_GREEN_TWO_PRESSED: Int = 0xff50BAAB.toInt()
        var BASE_COLOR_ORANGE_THREE: Int = 0xffFF8D00.toInt()
        var BASE_COLOR_YELLOW_FOUR: Int = 0xffFFD000.toInt()
        var BASE_COLOR_GRAY: Int = 0xffd8d8d8.toInt()
        var BASE_COLOR_DISABLED: Int = 0XFFC2C2C2.toInt()

        var BASE_BUTTON_GREEN_DRAWABLE: Drawable? = null
            get() {
                return StateListDrawable().apply {
                    addState(intArrayOf(-android.R.attr.state_enabled), GradientDrawable().apply {
                        setColor(StyleColorUtils.BASE_COLOR_GRAY)
                        cornerRadius = SizeUtils.dp2px(4.0f).toFloat()
                    })
                    addState(intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_pressed), GradientDrawable().apply {
                        setColor(StyleColorUtils.BASE_COLOR_GREEN_TWO_NORMAL)
                        cornerRadius = SizeUtils.dp2px(4.0f).toFloat()
                    })
                    addState(intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed), GradientDrawable().apply {
                        setColor(StyleColorUtils.BASE_COLOR_GREEN_TWO_PRESSED)
                        cornerRadius = SizeUtils.dp2px(4.0f).toFloat()
                    })
                }
            }

        var BASE_SELECT_GREEN_DRAWABLE: Drawable? = null
            get() {
                return StateListDrawable().apply {
                    addState(intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_pressed), GradientDrawable().apply {
                        setColor(StyleColorUtils.BASE_COLOR_WHITE_ONE)
                        cornerRadius = SizeUtils.dp2px(20.0f).toFloat()
                    })
                    addState(intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed), GradientDrawable().apply {
                        setColor(StyleColorUtils.BASE_COLOR_GREEN_TWO_NORMAL)
                        cornerRadius = SizeUtils.dp2px(20.0f).toFloat()
                    })
                }
            }

        var BASE_BUTTON_PURPLE_DRAWABLE: Drawable? = null
            get() {
                return StateListDrawable().apply {
                    addState(intArrayOf(-android.R.attr.state_enabled), GradientDrawable().apply {
                        setColor(StyleColorUtils.BASE_COLOR_GRAY)
                        cornerRadius = SizeUtils.dp2px(4.0f).toFloat()
                    })
                    addState(intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_pressed), GradientDrawable().apply {
                        setColor(StyleColorUtils.BASE_COLOR_PURPLE_ONE_NORMAL)
                        cornerRadius = SizeUtils.dp2px(4.0f).toFloat()
                    })
                    addState(intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed), GradientDrawable().apply {
                        setColor(StyleColorUtils.BASE_COLOR_PURPLE_ONE_PRESSED)
                        cornerRadius = SizeUtils.dp2px(4.0f).toFloat()
                    })
                }
            }

        var BASE_BACKGROUND_GREEN_DRAWABLE_ZT: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(BASE_COLOR_PURPLE_ONE_NORMAL)
                    cornerRadius = SizeUtils.dp2px(18.0f).toFloat()
                }
            }

        var BASE_BACKGROUND_GREEN_DRAWABLE: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(BASE_COLOR_GREEN_TWO_PRESSED)
                    cornerRadii = floatArrayOf(
                            SizeUtils.dp2px(13.33f).toFloat(), SizeUtils.dp2px(13.33f).toFloat()
                            , SizeUtils.dp2px(13.33f).toFloat(), SizeUtils.dp2px(13.33f).toFloat(), 0f, 0f, 0f, 0f
                    )
                }
            }

        var BASE_BACKGROUND_YELLOW_DRAWABLE: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(BASE_COLOR_YELLOW_FOUR)
                    cornerRadii = floatArrayOf(
                            SizeUtils.dp2px(13.33f).toFloat(), SizeUtils.dp2px(13.33f).toFloat()
                            , SizeUtils.dp2px(13.33f).toFloat(), SizeUtils.dp2px(13.33f).toFloat(), 0f, 0f, 0f, 0f
                    )
                }
            }

        var BASE_BACKGROUND_ORANGE_DRAWABLE: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(BASE_COLOR_ORANGE_THREE)
                    cornerRadii = floatArrayOf(
                            SizeUtils.dp2px(13.33f).toFloat(), SizeUtils.dp2px(13.33f).toFloat()
                            , SizeUtils.dp2px(13.33f).toFloat(), SizeUtils.dp2px(13.33f).toFloat(), 0f, 0f, 0f, 0f
                    )
                }
            }

        var BASE_BACKGROUND_PURPLE_DRAWABLE: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(BASE_COLOR_PURPLE_ONE_NORMAL)
                    cornerRadii = floatArrayOf(
                            SizeUtils.dp2px(13.33f).toFloat(), SizeUtils.dp2px(13.33f).toFloat()
                            , SizeUtils.dp2px(13.33f).toFloat(), SizeUtils.dp2px(13.33f).toFloat(), 0f, 0f, 0f, 0f
                    )
                }
            }

        //选中桌台状态 动态设置
        var BASE_ROUND_PURPLE_DRAWABLE: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(BASE_COLOR_PURPLE_ONE_NORMAL)
                    setSize(5, 5)
                    setStroke(2, BASE_COLOR_WHITE_ONE)
                }
            }


        var SHAPE_TABLE_MERGE_MARK: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(BASE_COLOR_ORANGE_THREE)
                    var radius = SizeUtils.dp2px(10f).toFloat()
                    cornerRadii = floatArrayOf(radius, radius, 0f, 0f, 0f, 0f, radius, radius)
                }
            }
        var SHAPE_TABLE_SETTLEMENT_MARK: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(BASE_COLOR_GREEN_TWO_NORMAL)
                    var radius = SizeUtils.dp2px(10f).toFloat()
                    cornerRadii = floatArrayOf(radius, radius, 0f, 0f, 0f, 0f, radius, radius)
                }
            }
        var SHAPE_TABLE_WHITE_MARK: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(Color.WHITE)
                    shape = GradientDrawable.OVAL
                    cornerRadius = SizeUtils.dp2px(11f).toFloat()
                }
            }
        var SHAPE_TABLE_RESERVE_MARK: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(BASE_COLOR_YELLOW_FOUR)
                    var radius = SizeUtils.dp2px(10f).toFloat()
                    cornerRadii = floatArrayOf(radius, radius, 0f, 0f, 0f, 0f, radius, radius)
                }
            }


        var SHAPE_GOODS_HANG: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(BASE_COLOR_PURPLE_ONE_NORMAL)
                    shape = GradientDrawable.OVAL
                    cornerRadius = SizeUtils.dp2px(8f).toFloat()
                }
            }
        var SHAPE_GOODS_SERVING: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(BASE_COLOR_DISABLED)
                    shape = GradientDrawable.OVAL
                    cornerRadius = SizeUtils.dp2px(8f).toFloat()
                }
            }
        var SHAPE_GOODS_URGE: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(BASE_COLOR_ORANGE_THREE)
                    shape = GradientDrawable.OVAL
                    cornerRadius = SizeUtils.dp2px(8f).toFloat()
                }
            }
        var SHAPE_GOODS_CALL: Drawable? = null
            get() {
                return GradientDrawable().apply {
                    setColor(BASE_COLOR_YELLOW_FOUR)
                    shape = GradientDrawable.OVAL
                    cornerRadius = SizeUtils.dp2px(8f).toFloat()
                }
            }

        fun getGoodsTypeShape(color: Int): Drawable {
            return GradientDrawable().apply {
                setColor(Color.WHITE)
                shape = GradientDrawable.RECTANGLE
                cornerRadius = SizeUtils.dp2px(3f).toFloat()
                setStroke(SizeUtils.dp2px(1f), color)
            }
        }

        fun getRoundCorner(strokeWidthDp: Int, cornerRadiusDp: Int, fillColor: Int, borderColor: Int): Drawable {
            return GradientDrawable().apply {
                setColor(fillColor)
                shape = GradientDrawable.RECTANGLE
                cornerRadius = SizeUtils.dp2px(cornerRadiusDp.toFloat()).toFloat()
                setStroke(SizeUtils.dp2px(strokeWidthDp.toFloat()), borderColor)
            }
        }

        fun getRoundStateListDrawable(cornerRadiusDp: Float, normalColor: Int, pressedColor: Int, disableColor: Int): Drawable {
            var cornerRadius = SizeUtils.dp2px(cornerRadiusDp).toFloat()
            return StateListDrawable().apply {
                addState(intArrayOf(-android.R.attr.state_enabled), GradientDrawable().apply {
                    setColor(disableColor)
                    this.cornerRadius = cornerRadius
                })
                addState(intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_pressed), GradientDrawable().apply {
                    setColor(normalColor)
                    this.cornerRadius = cornerRadius
                })
                addState(intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed), GradientDrawable().apply {
                    setColor(pressedColor)
                    this.cornerRadius = cornerRadius
                })
            }
        }
    }
}
