lib_BIN = Ender.jar
lib_SRC = \
$(top_srcdir)/src/lib/common/annotations/Transfer.java \
$(top_srcdir)/src/lib/common/EnderNative.java \
$(top_srcdir)/src/lib/common/EnderTypeConverter.java \
$(top_srcdir)/src/lib/common/ReferenceableObject.java \
$(top_srcdir)/src/lib/Ender.java \
$(top_srcdir)/src/lib/Notation.java \
$(top_srcdir)/src/lib/Case.java \
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

lib_FLAGS = -cp /usr/share/java/jna.jar

$(top_builddir)/$(lib_BIN): $(lib_SRC)
	$(JAVAC) -d $(top_builddir)/src/lib $(lib_FLAGS) $(lib_SRC)
	jar cvf $(top_builddir)/src/lib/$(lib_BIN) -C $(top_builddir)/src/lib/ org

enderlibdir = $(libdir)
enderlib_DATA = $(top_builddir)/$(lib_BIN)
