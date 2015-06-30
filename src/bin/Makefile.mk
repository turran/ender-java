bin_BIN = ender2java
bin_SRC = $(top_srcdir)/src/bin/ender2java.java

$(top_builddir)/$(bin_BIN): $(bin_SRC) $(top_builddir)/$(lib_BIN)
	$(JAVAC) -d $(top_builddir)/src/bin  -cp $(top_builddir)/src/lib/Ender.jar $(bin_SRC)

enderbindir = $(bindir)
enderbin_DATA = $(top_builddir)/$(bin_BIN)

