lib_BIN = Ender.jar
lib_SRC = \
$(top_srcdir)/src/lib/api/EinaAPI.java \
$(top_srcdir)/src/lib/api/EnderAPI.java \
$(top_srcdir)/src/lib/api/ItemAPI.java \
$(top_srcdir)/src/lib/api/LibAPI.java \
$(top_srcdir)/src/lib/Ender.java \
$(top_srcdir)/src/lib/Item.java \
$(top_srcdir)/src/lib/ItemBasic.java \
$(top_srcdir)/src/lib/ItemObject.java \
$(top_srcdir)/src/lib/ItemType.java \
$(top_srcdir)/src/lib/ItemTransfer.java \
$(top_srcdir)/src/lib/Lib.java \
$(top_srcdir)/src/lib/ValueType.java

lib_FLAGS = -cp /usr/share/java/jna.jar

$(top_builddir)/$(lib_BIN): $(lib_SRC)
	$(JAVAC) -d $(top_builddir)/src/lib $(lib_FLAGS) $(lib_SRC)
	jar cvf $(top_builddir)/src/lib/$(lib_BIN) -C $(top_builddir)/src/lib/ org

enderlibdir = $(libdir)
enderlib_DATA = $(top_builddir)/$(lib_BIN)
