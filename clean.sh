#!/bin/bash

# Run git add .
git add .

# Remove .gradle and .androidide files
git rm -r --cached ".gradle"
git rm -r --cached ".androidide"

# Remove mydir directory
git reset clean.sh


