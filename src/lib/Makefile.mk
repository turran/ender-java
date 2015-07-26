lib_FLAGS = -cp /usr/share/java/jna.jar:/usr/share/java/codemodel.jar

lib_BIN = Ender.jar
lib_SRC = \
$(top_srcdir)/src/lib/common/annotations/Transfer.java \
$(top_srcdir)/src/lib/common/EnderNative.java \
$(top_srcdir)/src/lib/common/EnderTypeConverter.java \
$(top_srcdir)/src/lib/common/ReferenceableObject.java \
$(top_srcdir)/src/lib/Ender.java \
$(top_srcdir)/src/lib/Notation.java \
$(top_srcdir)/src/lib/Case.java \
$(top_srcdir)/src/lib/Generator.java \
$(top_srcdir)/src/lib/Item.java \
$(top_srcdir)/src/lib/ItemDef.java \
$(top_srcdir)/src/lib/ItemEnum.java \
$(top_srcdir)/src/lib/ItemAttr.java \
$(top_srcdir)/src/lib/ItemAttrFlag.java \
$(top_srcdir)/src/lib/ItemArg.java \
$(top_srcdir)/src/lib/ItemArgFlag.java \
$(top_srcdir)/src/lib/ItemArgDirection.java \
$(top_srcdir)/src/lib/ItemBasic.java \
$(top_srcdir)/src/lib/ItemObject.java \
$(top_srcdir)/src/lib/ItemFunction.java \
$(top_srcdir)/src/lib/ItemFunctionFlag.java \
$(top_srcdir)/src/lib/ItemStruct.java \
$(top_srcdir)/src/lib/ItemType.java \
$(top_srcdir)/src/lib/ItemTransfer.java \
$(top_srcdir)/src/lib/Lib.java \
$(top_srcdir)/src/lib/ValueType.java \
$(top_srcdir)/src/lib/org/eina/List.java

$(top_builddir)/$(lib_BIN): $(lib_SRC)
	$(JAVAC) -d $(top_builddir)/src/lib $(lib_FLAGS) $(lib_SRC)
	jar cvf $(top_builddir)/src/lib/$(lib_BIN) -C $(top_builddir)/src/lib/ org

enderlibdir = $(libdir)
enderlib_DATA = $(top_builddir)/$(lib_BIN)

# To build the enesim sources
enesim_lib_BIN = Enesim.jar
enesim_lib_SRC = \
$(top_srcdir)/src/lib/org/enesim/RendererDispmap.java \
$(top_srcdir)/src/lib/org/enesim/RendererShape.java \
$(top_srcdir)/src/lib/org/enesim/RendererTransition.java \
$(top_srcdir)/src/lib/org/enesim/RendererProxy.java \
$(top_srcdir)/src/lib/org/enesim/RendererGrid.java \
$(top_srcdir)/src/lib/org/enesim/RendererStripes.java \
$(top_srcdir)/src/lib/org/enesim/Converter.java \
$(top_srcdir)/src/lib/org/enesim/PoolSw.java \
$(top_srcdir)/src/lib/org/enesim/RendererEllipse.java \
$(top_srcdir)/src/lib/org/enesim/RendererCircle.java \
$(top_srcdir)/src/lib/org/enesim/RendererMapQuad.java \
$(top_srcdir)/src/lib/org/enesim/RendererRectangle.java \
$(top_srcdir)/src/lib/org/enesim/Buffer.java \
$(top_srcdir)/src/lib/org/enesim/RendererImage.java \
$(top_srcdir)/src/lib/org/enesim/RendererLine.java \
$(top_srcdir)/src/lib/org/enesim/Log.java \
$(top_srcdir)/src/lib/org/enesim/Path.java \
$(top_srcdir)/src/lib/org/enesim/StreamFile.java \
$(top_srcdir)/src/lib/org/enesim/RendererCompound.java \
$(top_srcdir)/src/lib/org/enesim/RendererGradient.java \
$(top_srcdir)/src/lib/org/enesim/RendererClipper.java \
$(top_srcdir)/src/lib/org/enesim/text/EngineFreetype.java \
$(top_srcdir)/src/lib/org/enesim/text/Font.java \
$(top_srcdir)/src/lib/org/enesim/text/Buffer.java \
$(top_srcdir)/src/lib/org/enesim/text/BufferSimple.java \
$(top_srcdir)/src/lib/org/enesim/text/BufferSmart.java \
$(top_srcdir)/src/lib/org/enesim/text/Engine.java \
$(top_srcdir)/src/lib/org/enesim/RendererPath.java \
$(top_srcdir)/src/lib/org/enesim/ImageProvider.java \
$(top_srcdir)/src/lib/org/enesim/RendererPerlin.java \
$(top_srcdir)/src/lib/org/enesim/ImageContext.java \
$(top_srcdir)/src/lib/org/enesim/Surface.java \
$(top_srcdir)/src/lib/org/enesim/StreamBuffer.java \
$(top_srcdir)/src/lib/org/enesim/RendererTextSpan.java \
$(top_srcdir)/src/lib/org/enesim/RendererPattern.java \
$(top_srcdir)/src/lib/org/enesim/RendererCompoundLayer.java \
$(top_srcdir)/src/lib/org/enesim/Pool.java \
$(top_srcdir)/src/lib/org/enesim/RendererFigure.java \
$(top_srcdir)/src/lib/org/enesim/RendererChecker.java \
$(top_srcdir)/src/lib/org/enesim/RendererGradientRadial.java \
$(top_srcdir)/src/lib/org/enesim/StreamBase64.java \
$(top_srcdir)/src/lib/org/enesim/PoolEina.java \
$(top_srcdir)/src/lib/org/enesim/RendererGradientLinear.java \
$(top_srcdir)/src/lib/org/enesim/Image.java \
$(top_srcdir)/src/lib/org/enesim/Renderer.java \
$(top_srcdir)/src/lib/org/enesim/RendererBackground.java \
$(top_srcdir)/src/lib/org/enesim/Stream.java \
$(top_srcdir)/src/lib/org/enesim/RendererRaddist.java \
$(top_srcdir)/src/lib/org/enesim/ImageFile.java \
$(top_srcdir)/src/lib/org/enesim/RendererBlur.java \
$(top_srcdir)/src/lib/org/enesim/RendererImporter.java

# $(top_builddir)/$(enesim_lib_BIN): $(enesim_lib_SRC)
# 	$(JAVAC) -d $(top_builddir)/src/lib $(lib_FLAGS) $(enesim_lib_SRC)
#	jar cvf $(top_builddir)/src/lib/$(enesim_lib_BIN) -C $(top_builddir)/src/lib/ org

# enesimlibdir = $(libdir)
# enesimlib_DATA = $(top_builddir)/$(enesim_lib_BIN)
