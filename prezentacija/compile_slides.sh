#!/usr/bin/env bash
pandoc -V theme:metropolis -V classoption=dvipsnames slides.md -t beamer -o slides.pdf

