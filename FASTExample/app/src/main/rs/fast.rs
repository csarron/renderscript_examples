// Needed directive for RS to work
#pragma version(1)
#pragma rs_fp_relaxed

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.fastexample)

#include "harris.rsh"

int fastThreshold = 20;

int stride;
int pixelOffsets[16];
void makeOffsets(int row_stride)
{
    stride = row_stride;

    pixelOffsets[0] = 0 + row_stride * 3;
    pixelOffsets[1] = 1 + row_stride * 3;
    pixelOffsets[2] = 2 + row_stride * 2;
    pixelOffsets[3] = 3 + row_stride * 1;
    pixelOffsets[4] = 3 + row_stride * 0;
    pixelOffsets[5] = 3 + row_stride * -1;
    pixelOffsets[6] = 2 + row_stride * -2;
    pixelOffsets[7] = 1 + row_stride * -3;
    pixelOffsets[8] = 0 + row_stride * -3;
    pixelOffsets[9] = -1 + row_stride * -3;
    pixelOffsets[10] = -2 + row_stride * -2;
    pixelOffsets[11] = -3 + row_stride * -1;
    pixelOffsets[12] = -3 + row_stride * 0;
    pixelOffsets[13] = -3 + row_stride * 1;
    pixelOffsets[14] = -2 + row_stride * 2;
    pixelOffsets[15] = -1 + row_stride * 3;
}

// The following code is a direct porting of the original FAST library
// (http://www.edwardrosten.com/work/fast.html), which is highly optimized, auto
// generated code.
void fastOptimized(const uchar * v_in, uchar * v_out)
{

    int cb = *v_in + fastThreshold;
    int c_b = *v_in - fastThreshold;
    if (v_in[pixelOffsets[0]] > cb) if (v_in[pixelOffsets[1]] > cb) if (v_in[pixelOffsets[2]] > cb) if (v_in[pixelOffsets[3]] >
                        cb) if (
                                v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] >
                                                    cb) if (
                                                            v_in[pixelOffsets[8]] > cb) { }
                                    else if (v_in[pixelOffsets[15]] > cb) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (v_in[pixelOffsets[9]] < c_b) if (
                                                    v_in[pixelOffsets[10]] <
                                                    c_b) if (
                                                            v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (
                                                                            v_in[pixelOffsets[15]] <
                                                                            c_b) { }
                                                                else {
                                                                    *v_out = 0;
                                                                    return;
                                                                }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[15]] > cb) if (v_in[pixelOffsets[13]] > cb) if (
                                            v_in[pixelOffsets[14]] >
                                            cb) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                                    v_in[pixelOffsets[9]] <
                                                    c_b) if (
                                                            v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                                                            v_in[pixelOffsets[14]] <
                                                                            c_b) { }
                                                                else {
                                                                    *v_out = 0;
                                                                    return;
                                                                }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (v_in[pixelOffsets[9]] < c_b) if (
                                                v_in[pixelOffsets[10]] <
                                                c_b) if (
                                                        v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (
                                                                        v_in[pixelOffsets[14]] <
                                                                        c_b) { }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                            v_in[pixelOffsets[9]] <
                                            c_b) if (
                                                    v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                                                    v_in[pixelOffsets[14]] <
                                                                    c_b) if (
                                                                            v_in[pixelOffsets[15]] < c_b) { }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[12]] > cb) if (
                                        v_in[pixelOffsets[13]] >
                                        cb) if (
                                                v_in[pixelOffsets[15]] > cb) { }
                                        else if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] >
                                                            cb) if (
                                                                    v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) { }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (
                                                v_in[pixelOffsets[8]] <
                                                c_b) if (
                                                        v_in[pixelOffsets[9]] < c_b) if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (
                                                                        v_in[pixelOffsets[13]] <
                                                                        c_b) { }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                            v_in[pixelOffsets[9]] <
                                            c_b) if (
                                                    v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                                                    v_in[pixelOffsets[13]] <
                                                                    c_b) if (
                                                                            v_in[pixelOffsets[6]] < c_b) { }
                                                            else if (v_in[pixelOffsets[15]] < c_b) { }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                            v_in[pixelOffsets[9]] <
                                            c_b) if (
                                                    v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                                                    v_in[pixelOffsets[13]] <
                                                                    c_b) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) if (
                                        v_in[pixelOffsets[15]] >
                                        cb) { }
                                    else if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] >
                                                        cb) if (
                                                                v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                        v_in[pixelOffsets[9]] <
                                        c_b) if (
                                                v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (
                                                                v_in[pixelOffsets[14]] <
                                                                c_b) if (
                                                                        v_in[pixelOffsets[6]] < c_b) { }
                                                        else if (v_in[pixelOffsets[15]] < c_b) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[11]] > cb) if (
                                    v_in[pixelOffsets[12]] >
                                    cb) if (
                                            v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                        else if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] >
                                                            cb) if (
                                                                    v_in[pixelOffsets[10]] > cb) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] >
                                                        cb) if (
                                                                v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[10]] > cb) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (
                                            v_in[pixelOffsets[7]] <
                                            c_b) if (
                                                    v_in[pixelOffsets[8]] < c_b) if (v_in[pixelOffsets[9]] < c_b) if (v_in[pixelOffsets[10]] < c_b) if (
                                                                    v_in[pixelOffsets[12]] <
                                                                    c_b) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                        v_in[pixelOffsets[9]] <
                                        c_b) if (
                                                v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                                                v_in[pixelOffsets[6]] <
                                                                c_b) if (
                                                                        v_in[pixelOffsets[5]] < c_b) { }
                                                        else if (v_in[pixelOffsets[14]] < c_b) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else if (v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (
                                        v_in[pixelOffsets[8]] <
                                        c_b) if (
                                                v_in[pixelOffsets[9]] < c_b) if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (
                                                                v_in[pixelOffsets[12]] <
                                                                c_b) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (
                                    v_in[pixelOffsets[14]] >
                                    cb) if (
                                            v_in[pixelOffsets[15]] > cb) { }
                                    else if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] >
                                                        cb) if (
                                                                v_in[pixelOffsets[10]] > cb) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] >
                                                    cb) if (
                                                            v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[10]] > cb) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                    v_in[pixelOffsets[9]] <
                                    c_b) if (
                                            v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (
                                                            v_in[pixelOffsets[6]] <
                                                            c_b) if (
                                                                    v_in[pixelOffsets[5]] < c_b) { }
                                                    else if (v_in[pixelOffsets[14]] < c_b) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else if (v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else if (v_in[pixelOffsets[3]] < c_b) if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (
                                v_in[pixelOffsets[12]] >
                                cb) if (
                                        v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                        else if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] >
                                                            cb) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] >
                                                        cb) if (
                                                                v_in[pixelOffsets[9]] > cb) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] >
                                                    cb) if (
                                                            v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] > cb) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                    v_in[pixelOffsets[9]] <
                                    c_b) if (
                                            v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (
                                                            v_in[pixelOffsets[4]] <
                                                            c_b) { }
                                                else if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) if (
                                                        v_in[pixelOffsets[15]] <
                                                        c_b) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (
                                v_in[pixelOffsets[13]] >
                                cb) if (
                                        v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                    else if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] >
                                                        cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] >
                                                    cb) if (
                                                            v_in[pixelOffsets[9]] > cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] >
                                                cb) if (
                                                        v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] > cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                v_in[pixelOffsets[9]] <
                                c_b) if (
                                        v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (
                                                        v_in[pixelOffsets[5]] <
                                                        c_b) if (
                                                                v_in[pixelOffsets[4]] < c_b) { }
                                                else if (v_in[pixelOffsets[13]] < c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else if (v_in[pixelOffsets[2]] < c_b) if (v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[10]] > cb) if (
                            v_in[pixelOffsets[11]] >
                            cb) if (
                                    v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) if (
                                                    v_in[pixelOffsets[15]] >
                                                    cb) { }
                                        else if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] >
                                                        cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] >
                                                    cb) if (
                                                            v_in[pixelOffsets[8]] > cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[3]] > cb) if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] >
                                                cb) if (
                                                        v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else if (v_in[pixelOffsets[9]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                v_in[pixelOffsets[10]] <
                                c_b) if (
                                        v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[4]] < c_b) if (
                                                        v_in[pixelOffsets[3]] <
                                                        c_b) { }
                                            else if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (
                                                    v_in[pixelOffsets[14]] <
                                                    c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (
                                                v_in[pixelOffsets[14]] <
                                                c_b) if (
                                                        v_in[pixelOffsets[15]] < c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else if (v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (
                            v_in[pixelOffsets[12]] >
                            cb) if (
                                    v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                    else if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] >
                                                    cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] >
                                                cb) if (
                                                        v_in[pixelOffsets[8]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[3]] > cb) if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] >
                                            cb) if (
                                                    v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else if (v_in[pixelOffsets[9]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                            v_in[pixelOffsets[10]] <
                            c_b) if (
                                    v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (
                                                    v_in[pixelOffsets[4]] <
                                                    c_b) if (
                                                            v_in[pixelOffsets[3]] < c_b) { }
                                            else if (v_in[pixelOffsets[12]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) if (
                                                v_in[pixelOffsets[15]] <
                                                c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else {
                *v_out = 0;
                return;
            }
        else if (v_in[pixelOffsets[1]] < c_b) if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] > cb) if (
                        v_in[pixelOffsets[10]] >
                        cb) if (
                                v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (
                                                v_in[pixelOffsets[14]] >
                                                cb) if (
                                                        v_in[pixelOffsets[15]] > cb) { }
                                        else if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] >
                                                    cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[3]] > cb) if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] >
                                                cb) if (
                                                        v_in[pixelOffsets[7]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[2]] > cb) if (v_in[pixelOffsets[3]] > cb) if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] >
                                            cb) if (
                                                    v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else if (v_in[pixelOffsets[8]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[9]] < c_b) if (
                            v_in[pixelOffsets[6]] <
                            c_b) if (
                                    v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[3]] < c_b) if (
                                                    v_in[pixelOffsets[2]] <
                                                    c_b) { }
                                        else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                                v_in[pixelOffsets[13]] <
                                                c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                            v_in[pixelOffsets[13]] <
                                            c_b) if (
                                                    v_in[pixelOffsets[14]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                        v_in[pixelOffsets[13]] <
                                        c_b) if (
                                                v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else {
                *v_out = 0;
                return;
            }
        else if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[10]] > cb) if (
                        v_in[pixelOffsets[11]] >
                        cb) if (
                                v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) if (
                                                v_in[pixelOffsets[15]] >
                                                cb) { }
                                    else if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] >
                                                cb) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[3]] > cb) if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] >
                                            cb) if (
                                                    v_in[pixelOffsets[7]] > cb) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else if (v_in[pixelOffsets[2]] > cb) if (v_in[pixelOffsets[3]] > cb) if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[5]] >
                                        cb) if (
                                                v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else {
                *v_out = 0;
                return;
            }
        else if (v_in[pixelOffsets[8]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[9]] < c_b) if (
                        v_in[pixelOffsets[10]] <
                        c_b) if (
                                v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[4]] < c_b) if (
                                                v_in[pixelOffsets[3]] <
                                                c_b) if (
                                                        v_in[pixelOffsets[2]] < c_b) { }
                                        else if (v_in[pixelOffsets[11]] < c_b) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (
                                            v_in[pixelOffsets[14]] <
                                            c_b) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (
                                        v_in[pixelOffsets[14]] <
                                        c_b) if (
                                                v_in[pixelOffsets[15]] < c_b) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else {
                *v_out = 0;
                return;
            }
        else {
            *v_out = 0;
            return;
        }
    else if (v_in[pixelOffsets[0]] < c_b) if (v_in[pixelOffsets[1]] > cb) if (v_in[pixelOffsets[8]] > cb) if (
                    v_in[pixelOffsets[7]] >
                    cb) if (v_in[pixelOffsets[9]] >
                                cb) if (
                                        v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[3]] >
                                                            cb) if (
                                                                    v_in[pixelOffsets[2]] > cb) { }
                                        else if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (
                                                v_in[pixelOffsets[13]] >
                                                cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (
                                            v_in[pixelOffsets[13]] >
                                            cb) if (
                                                    v_in[pixelOffsets[14]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (
                                        v_in[pixelOffsets[13]] >
                                        cb) if (
                                                v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else if (v_in[pixelOffsets[8]] < c_b) if (v_in[pixelOffsets[9]] < c_b) if (v_in[pixelOffsets[10]] < c_b) if (
                            v_in[pixelOffsets[11]] <
                            c_b) if (
                                    v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) if (
                                                    v_in[pixelOffsets[15]] <
                                                    c_b) { }
                                        else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (
                                                v_in[pixelOffsets[7]] <
                                                c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[3]] < c_b) if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (
                                            v_in[pixelOffsets[6]] <
                                            c_b) if (
                                                    v_in[pixelOffsets[7]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[2]] < c_b) if (v_in[pixelOffsets[3]] < c_b) if (v_in[pixelOffsets[4]] < c_b) if (
                                        v_in[pixelOffsets[5]] <
                                        c_b) if (
                                                v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else {
                *v_out = 0;
                return;
            }
        else if (v_in[pixelOffsets[1]] < c_b) if (v_in[pixelOffsets[2]] > cb) if (v_in[pixelOffsets[9]] > cb) if (
                        v_in[pixelOffsets[7]] >
                        cb) if (v_in[pixelOffsets[8]] >
                                    cb) if (
                                            v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[4]] >
                                                                cb) if (
                                                                        v_in[pixelOffsets[3]] > cb) { }
                                            else if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (
                                                    v_in[pixelOffsets[14]] >
                                                    cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (
                                                v_in[pixelOffsets[14]] >
                                                cb) if (
                                                        v_in[pixelOffsets[15]] > cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else if (v_in[pixelOffsets[9]] < c_b) if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (
                                v_in[pixelOffsets[12]] <
                                c_b) if (
                                        v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                        else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (
                                                    v_in[pixelOffsets[8]] <
                                                    c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (
                                                v_in[pixelOffsets[7]] <
                                                c_b) if (
                                                        v_in[pixelOffsets[8]] < c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[3]] < c_b) if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (
                                            v_in[pixelOffsets[6]] <
                                            c_b) if (
                                                    v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else if (v_in[pixelOffsets[2]] < c_b) if (v_in[pixelOffsets[3]] > cb) if (v_in[pixelOffsets[10]] > cb) if (
                            v_in[pixelOffsets[7]] >
                            cb) if (
                                    v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[6]] >
                                                        cb) if (
                                                                v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[4]] > cb) { }
                                                else if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) if (
                                                        v_in[pixelOffsets[15]] >
                                                        cb) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                    v_in[pixelOffsets[13]] <
                                    c_b) if (
                                            v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                        else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                                        v_in[pixelOffsets[9]] <
                                                        c_b) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (
                                                    v_in[pixelOffsets[8]] <
                                                    c_b) if (
                                                            v_in[pixelOffsets[9]] < c_b) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (
                                                v_in[pixelOffsets[7]] <
                                                c_b) if (
                                                        v_in[pixelOffsets[8]] < c_b) if (v_in[pixelOffsets[9]] < c_b) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else if (v_in[pixelOffsets[3]] < c_b) if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[13]] > cb) if (
                                v_in[pixelOffsets[7]] >
                                cb) if (
                                        v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[10]] > cb) if (
                                                        v_in[pixelOffsets[11]] >
                                                        cb) if (v_in[pixelOffsets[12]] >
                                                                    cb) if (
                                                                            v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[5]] > cb) { }
                                                        else if (v_in[pixelOffsets[14]] > cb) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else if (v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[5]] > cb) if (
                                        v_in[pixelOffsets[6]] >
                                        cb) if (
                                                v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[10]] >
                                                                    cb) if (
                                                                            v_in[pixelOffsets[12]] > cb) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[14]] < c_b) if (
                                            v_in[pixelOffsets[15]] <
                                            c_b) { }
                                        else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                                        v_in[pixelOffsets[9]] <
                                                        c_b) if (
                                                                v_in[pixelOffsets[10]] < c_b) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (
                                                    v_in[pixelOffsets[8]] <
                                                    c_b) if (
                                                            v_in[pixelOffsets[9]] < c_b) if (v_in[pixelOffsets[10]] < c_b) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] >
                                            cb) if (
                                                    v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (
                                                                    v_in[pixelOffsets[12]] >
                                                                    cb) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[14]] > cb) if (
                                    v_in[pixelOffsets[7]] >
                                    cb) if (
                                            v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[10]] > cb) if (
                                                            v_in[pixelOffsets[11]] >
                                                            cb) if (v_in[pixelOffsets[12]] >
                                                                        cb) if (
                                                                                v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[6]] > cb) { }
                                                            else if (v_in[pixelOffsets[15]] > cb) { }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[6]] > cb) if (
                                            v_in[pixelOffsets[7]] >
                                            cb) if (
                                                    v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[10]] > cb) if (
                                                                    v_in[pixelOffsets[11]] >
                                                                    cb) if (v_in[pixelOffsets[13]] >
                                                                                cb) { }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                        else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                                        v_in[pixelOffsets[9]] <
                                                        c_b) if (
                                                                v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) { }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] >
                                                cb) if (
                                                        v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (
                                                                        v_in[pixelOffsets[13]] >
                                                                        cb) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[15]] < c_b) if (
                                        v_in[pixelOffsets[13]] >
                                        cb) if (
                                                v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[10]] >
                                                                    cb) if (
                                                                            v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[14]] > cb) { }
                                                                else {
                                                                    *v_out = 0;
                                                                    return;
                                                                }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] > cb) if (
                                                v_in[pixelOffsets[10]] >
                                                cb) if (v_in[pixelOffsets[11]] >
                                                            cb) if (
                                                                    v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) { }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[14]] > cb) if (
                                            v_in[pixelOffsets[8]] >
                                            cb) if (
                                                    v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (
                                                                    v_in[pixelOffsets[12]] >
                                                                    cb) if (
                                                                            v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                                                else {
                                                                    *v_out = 0;
                                                                    return;
                                                                }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) { }
                                    else if (v_in[pixelOffsets[15]] < c_b) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (
                                            v_in[pixelOffsets[9]] >
                                            cb) if (
                                                    v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (
                                                                    v_in[pixelOffsets[14]] >
                                                                    cb) if (
                                                                            v_in[pixelOffsets[15]] > cb) { }
                                                            else {
                                                                *v_out = 0;
                                                                return;
                                                            }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (
                                        v_in[pixelOffsets[9]] >
                                        cb) if (
                                                v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[13]] > cb) if (
                                                                v_in[pixelOffsets[14]] >
                                                                cb) if (
                                                                        v_in[pixelOffsets[6]] > cb) { }
                                                        else if (v_in[pixelOffsets[15]] > cb) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) if (
                                        v_in[pixelOffsets[15]] <
                                        c_b) { }
                                    else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                                    v_in[pixelOffsets[9]] <
                                                    c_b) if (
                                                            v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) { }
                                                        else {
                                                            *v_out = 0;
                                                            return;
                                                        }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (
                                    v_in[pixelOffsets[9]] >
                                    cb) if (
                                            v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (
                                                            v_in[pixelOffsets[6]] >
                                                            cb) if (
                                                                    v_in[pixelOffsets[5]] > cb) { }
                                                    else if (v_in[pixelOffsets[14]] > cb) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else if (v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (
                                    v_in[pixelOffsets[14]] <
                                    c_b) if (
                                            v_in[pixelOffsets[15]] < c_b) { }
                                    else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                                    v_in[pixelOffsets[9]] <
                                                    c_b) if (
                                                            v_in[pixelOffsets[10]] < c_b) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (
                                                v_in[pixelOffsets[8]] <
                                                c_b) if (
                                                        v_in[pixelOffsets[9]] < c_b) if (v_in[pixelOffsets[10]] < c_b) { }
                                                    else {
                                                        *v_out = 0;
                                                        return;
                                                    }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (
                                v_in[pixelOffsets[9]] >
                                cb) if (
                                        v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[6]] > cb) if (
                                                        v_in[pixelOffsets[5]] >
                                                        cb) if (v_in[pixelOffsets[4]] >
                                                                    cb) { }
                                                else if (v_in[pixelOffsets[13]] > cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                v_in[pixelOffsets[13]] <
                                c_b) if (
                                        v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                    else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (
                                                    v_in[pixelOffsets[9]] <
                                                    c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (
                                                v_in[pixelOffsets[8]] <
                                                c_b) if (
                                                        v_in[pixelOffsets[9]] < c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (
                                            v_in[pixelOffsets[7]] <
                                            c_b) if (
                                                    v_in[pixelOffsets[8]] < c_b) if (v_in[pixelOffsets[9]] < c_b) { }
                                                else {
                                                    *v_out = 0;
                                                    return;
                                                }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else if (v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (
                            v_in[pixelOffsets[10]] >
                            cb) if (v_in[pixelOffsets[11]] >
                                        cb) if (
                                                v_in[pixelOffsets[6]] > cb) if (v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[3]] >
                                                                    cb) { }
                                            else if (v_in[pixelOffsets[12]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (v_in[pixelOffsets[14]] > cb) if (
                                                v_in[pixelOffsets[15]] >
                                                cb) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else if (v_in[pixelOffsets[9]] < c_b) if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (
                            v_in[pixelOffsets[12]] <
                            c_b) if (
                                    v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                    else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) if (
                                                v_in[pixelOffsets[8]] <
                                                c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (
                                            v_in[pixelOffsets[7]] <
                                            c_b) if (
                                                    v_in[pixelOffsets[8]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[3]] < c_b) if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (
                                        v_in[pixelOffsets[6]] <
                                        c_b) if (
                                                v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) { }
                                            else {
                                                *v_out = 0;
                                                return;
                                            }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else {
                *v_out = 0;
                return;
            }
        else if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[9]] > cb) if (
                        v_in[pixelOffsets[10]] >
                        cb) if (v_in[pixelOffsets[6]] >
                                    cb) if (
                                            v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[3]] > cb) if (v_in[pixelOffsets[2]] >
                                                                cb) { }
                                        else if (v_in[pixelOffsets[11]] > cb) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (
                                            v_in[pixelOffsets[14]] >
                                            cb) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (v_in[pixelOffsets[13]] > cb) if (
                                        v_in[pixelOffsets[14]] >
                                        cb) if (
                                                v_in[pixelOffsets[15]] > cb) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else {
                *v_out = 0;
                return;
            }
        else if (v_in[pixelOffsets[8]] < c_b) if (v_in[pixelOffsets[9]] < c_b) if (v_in[pixelOffsets[10]] < c_b) if (
                        v_in[pixelOffsets[11]] <
                        c_b) if (
                                v_in[pixelOffsets[12]] < c_b) if (v_in[pixelOffsets[13]] < c_b) if (v_in[pixelOffsets[14]] < c_b) if (
                                                v_in[pixelOffsets[15]] <
                                                c_b) { }
                                    else if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[6]] < c_b) if (
                                            v_in[pixelOffsets[7]] <
                                            c_b) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[3]] < c_b) if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[5]] < c_b) if (
                                        v_in[pixelOffsets[6]] <
                                        c_b) if (
                                                v_in[pixelOffsets[7]] < c_b) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else if (v_in[pixelOffsets[2]] < c_b) if (v_in[pixelOffsets[3]] < c_b) if (v_in[pixelOffsets[4]] < c_b) if (
                                    v_in[pixelOffsets[5]] <
                                    c_b) if (
                                            v_in[pixelOffsets[6]] < c_b) if (v_in[pixelOffsets[7]] < c_b) { }
                                        else {
                                            *v_out = 0;
                                            return;
                                        }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else {
                *v_out = 0;
                return;
            }
        else {
            *v_out = 0;
            return;
        }
    else if (v_in[pixelOffsets[7]] > cb) if (v_in[pixelOffsets[8]] > cb) if (v_in[pixelOffsets[9]] > cb) if (v_in[pixelOffsets[6]] >
                        cb) if (
                                v_in[pixelOffsets[5]] > cb) if (v_in[pixelOffsets[4]] > cb) if (v_in[pixelOffsets[3]] > cb) if (v_in[pixelOffsets[2]] >
                                                    cb) if (
                                                            v_in[pixelOffsets[1]] > cb) { }
                                    else if (v_in[pixelOffsets[10]] > cb) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (
                                        v_in[pixelOffsets[13]] >
                                        cb) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (
                                    v_in[pixelOffsets[13]] >
                                    cb) if (
                                            v_in[pixelOffsets[14]] > cb) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else if (v_in[pixelOffsets[10]] > cb) if (v_in[pixelOffsets[11]] > cb) if (v_in[pixelOffsets[12]] > cb) if (
                                v_in[pixelOffsets[13]] >
                                cb) if (
                                        v_in[pixelOffsets[14]] > cb) if (v_in[pixelOffsets[15]] > cb) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else {
                *v_out = 0;
                return;
            }
        else {
            *v_out = 0;
            return;
        }
    else if (v_in[pixelOffsets[7]] < c_b) if (v_in[pixelOffsets[8]] < c_b) if (v_in[pixelOffsets[9]] < c_b) if (
                    v_in[pixelOffsets[6]] <
                    c_b) if (
                            v_in[pixelOffsets[5]] < c_b) if (v_in[pixelOffsets[4]] < c_b) if (v_in[pixelOffsets[3]] < c_b) if (
                                            v_in[pixelOffsets[2]] <
                                            c_b) if (
                                                    v_in[pixelOffsets[1]] < c_b) { }
                                    else if (v_in[pixelOffsets[10]] < c_b) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                        v_in[pixelOffsets[13]] <
                                        c_b) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                    v_in[pixelOffsets[13]] <
                                    c_b) if (
                                            v_in[pixelOffsets[14]] < c_b) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else if (v_in[pixelOffsets[10]] < c_b) if (v_in[pixelOffsets[11]] < c_b) if (v_in[pixelOffsets[12]] < c_b) if (
                                v_in[pixelOffsets[13]] <
                                c_b) if (
                                        v_in[pixelOffsets[14]] < c_b) if (v_in[pixelOffsets[15]] < c_b) { }
                                    else {
                                        *v_out = 0;
                                        return;
                                    }
                                else {
                                    *v_out = 0;
                                    return;
                                }
                            else {
                                *v_out = 0;
                                return;
                            }
                        else {
                            *v_out = 0;
                            return;
                        }
                    else {
                        *v_out = 0;
                        return;
                    }
                else {
                    *v_out = 0;
                    return;
                }
            else {
                *v_out = 0;
                return;
            }
        else {
            *v_out = 0;
            return;
        }
    else {
        *v_out = 0;
        return;
    }

    // Here we calculate the harris score
    // calculateHarrisScore(uchar * v_in, int32_t x, uint32_t y, int row_stride)
    uchar score = calculateHarrisScore(v_in, stride);
    *v_out = score;
}
