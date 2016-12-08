#!/bin/sh

mkdir -p build/docs/doxygen
doxygen
doxygen Doxyfile_ja
