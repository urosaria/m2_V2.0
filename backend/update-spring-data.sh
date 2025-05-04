#!/bin/bash

# Update PageRequest constructor to of() method
find src -type f -name "*.java" -exec sed -i '' 's/new PageRequest(/PageRequest.of(/g' {} +

# Update findOne to findById().orElse(null)
find src -type f -name "*.java" -exec sed -i '' 's/\(return[[:space:]]*([^)]*)\)\{0,1\}[[:space:]]*this\.\([^.]*\)\.findOne(\([^)]*\));/return this.\2.findById(\3).orElse(null);/g' {} +

# Update variable assignments with findOne
find src -type f -name "*.java" -exec sed -i '' 's/\([^=]*\)[[:space:]]*=[[:space:]]*([^)]*)[[:space:]]*this\.\([^.]*\)\.findOne(\([^)]*\));/\1 = this.\2.findById(\3).orElse(null);/g' {} +

# Update Sort constructor in PageRequest
find src -type f -name "*.java" -exec sed -i '' 's/Sort\.Direction\.\([^,]*\),[[:space:]]*new String\[\][[:space:]]*{[[:space:]]*"\([^"]*\)"[[:space:]]*}/Sort.by(Sort.Direction.\1, "\2")/g' {} +
