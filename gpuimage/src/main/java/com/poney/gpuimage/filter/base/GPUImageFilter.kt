/*
 * Copyright (C) 2018 CyberAgent, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.poney.gpuimage.filter.base

import android.content.Context
import com.poney.gpuimage.utils.OpenGlUtils.loadProgram
import kotlin.jvm.JvmOverloads
import com.poney.gpuimage.utils.OpenGlUtils
import android.opengl.GLES20
import android.graphics.PointF
import java.io.InputStream
import java.lang.Exception
import java.nio.FloatBuffer
import java.util.*

open class GPUImageFilter @JvmOverloads constructor(
    vertexShader: String = NO_FILTER_VERTEX_SHADER,
    fragmentShader: String = NO_FILTER_FRAGMENT_SHADER
) {
    private val runOnDraw: LinkedList<Runnable> = LinkedList()
    private val vertexShader: String
    private val fragmentShader: String
    var program = 0
        private set
    var attribPosition = 0
        private set
    var uniformTexture = 0
        private set
    var attribTextureCoordinate = 0
        private set
    var outputWidth = 0
        private set
    var outputHeight = 0
        private set
    var isInitialized = false
        private set

    private fun init() {
        onInit()
        onInitialized()
    }

    open fun onInit() {
        program = loadProgram(vertexShader, fragmentShader)
        attribPosition = GLES20.glGetAttribLocation(program, "position")
        uniformTexture = GLES20.glGetUniformLocation(program, "inputImageTexture")
        attribTextureCoordinate = GLES20.glGetAttribLocation(program, "inputTextureCoordinate")
        isInitialized = true
    }

    open fun onInitialized() {}
    fun ifNeedInit() {
        if (!isInitialized) init()
    }

    fun destroy() {
        isInitialized = false
        GLES20.glDeleteProgram(program)
        onDestroy()
    }

    open fun onDestroy() {}
    open fun onOutputSizeChanged(width: Int, height: Int) {
        outputWidth = width
        outputHeight = height
    }

    open fun onDraw(
        textureId: Int, cubeBuffer: FloatBuffer,
        textureBuffer: FloatBuffer
    ) {
        GLES20.glUseProgram(program)
        runPendingOnDrawTasks()
        if (!isInitialized) {
            return
        }
        cubeBuffer.position(0)
        GLES20.glVertexAttribPointer(attribPosition, 2, GLES20.GL_FLOAT, false, 0, cubeBuffer)
        GLES20.glEnableVertexAttribArray(attribPosition)
        textureBuffer.position(0)
        GLES20.glVertexAttribPointer(
            attribTextureCoordinate, 2, GLES20.GL_FLOAT, false, 0,
            textureBuffer
        )
        GLES20.glEnableVertexAttribArray(attribTextureCoordinate)
        if (textureId != OpenGlUtils.NO_TEXTURE) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
            GLES20.glUniform1i(uniformTexture, 0)
        }
        onDrawArraysPre()
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDisableVertexAttribArray(attribPosition)
        GLES20.glDisableVertexAttribArray(attribTextureCoordinate)
        onDrawArraysAfter()
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
    }

    protected open fun onDrawArraysPre() {}
    protected open fun onDrawArraysAfter() {}
    protected fun runPendingOnDrawTasks() {
        synchronized(runOnDraw) {
            while (!runOnDraw.isEmpty()) {
                runOnDraw.removeFirst().run()
            }
        }
    }

    protected fun setInteger(location: Int, intValue: Int) {
        runOnDraw {
            ifNeedInit()
            GLES20.glUniform1i(location, intValue)
        }
    }

    protected fun setFloat(location: Int, floatValue: Float) {
        runOnDraw {
            ifNeedInit()
            GLES20.glUniform1f(location, floatValue)
        }
    }

    protected fun setFloatVec2(location: Int, arrayValue: FloatArray?) {
        runOnDraw {
            ifNeedInit()
            GLES20.glUniform2fv(location, 1, FloatBuffer.wrap(arrayValue))
        }
    }

    protected fun setFloatVec3(location: Int, arrayValue: FloatArray?) {
        runOnDraw {
            ifNeedInit()
            GLES20.glUniform3fv(location, 1, FloatBuffer.wrap(arrayValue))
        }
    }

    protected fun setFloatVec4(location: Int, arrayValue: FloatArray?) {
        runOnDraw {
            ifNeedInit()
            GLES20.glUniform4fv(location, 1, FloatBuffer.wrap(arrayValue))
        }
    }

    protected fun setFloatArray(location: Int, arrayValue: FloatArray) {
        runOnDraw {
            ifNeedInit()
            GLES20.glUniform1fv(location, arrayValue.size, FloatBuffer.wrap(arrayValue))
        }
    }

    protected fun setPoint(location: Int, point: PointF) {
        runOnDraw {
            ifNeedInit()
            val vec2 = FloatArray(2)
            vec2[0] = point.x
            vec2[1] = point.y
            GLES20.glUniform2fv(location, 1, vec2, 0)
        }
    }

    protected fun setUniformMatrix3f(location: Int, matrix: FloatArray?) {
        runOnDraw {
            ifNeedInit()
            GLES20.glUniformMatrix3fv(location, 1, false, matrix, 0)
        }
    }

    protected fun setUniformMatrix4f(location: Int, matrix: FloatArray?) {
        runOnDraw {
            ifNeedInit()
            GLES20.glUniformMatrix4fv(location, 1, false, matrix, 0)
        }
    }

    protected fun runOnDraw(runnable: Runnable) {
        synchronized(runOnDraw) { runOnDraw.addLast(runnable) }
    }

    companion object {
        const val NO_FILTER_VERTEX_SHADER = "" +
                "attribute vec4 position;\n" +
                "attribute vec4 inputTextureCoordinate;\n" +
                " \n" +
                "varying vec2 textureCoordinate;\n" +
                " \n" +
                "void main()\n" +
                "{\n" +
                "    gl_Position = position;\n" +
                "    textureCoordinate = inputTextureCoordinate.xy;\n" +
                "}"
        const val NO_FILTER_FRAGMENT_SHADER = "" +
                "varying highp vec2 textureCoordinate;\n" +
                " \n" +
                "uniform sampler2D inputImageTexture;\n" +
                " \n" +
                "void main()\n" +
                "{\n" +
                "     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n" +
                "}"

        fun loadShader(file: String?, context: Context): String {
            try {
                val assetManager = context.assets
                val ims = assetManager.open(file!!)
                val re = convertStreamToString(ims)
                ims.close()
                return re
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }

        fun convertStreamToString(`is`: InputStream?): String {
            val s = Scanner(`is`).useDelimiter("\\A")
            return if (s.hasNext()) s.next() else ""
        }
    }

    init {
        this.vertexShader = vertexShader
        this.fragmentShader = fragmentShader
    }
}