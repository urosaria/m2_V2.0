#!/bin/bash

# Update javax.persistence to jakarta.persistence
find src -type f -name "*.java" -exec sed -i '' 's/import javax\.persistence\./import jakarta.persistence./g' {} +

# Update javax.validation to jakarta.validation
find src -type f -name "*.java" -exec sed -i '' 's/import javax\.validation\./import jakarta.validation./g' {} +

# Update javax.servlet to jakarta.servlet
find src -type f -name "*.java" -exec sed -i '' 's/import javax\.servlet\./import jakarta.servlet./g' {} +

# Update any remaining javax packages to jakarta
find src -type f -name "*.java" -exec sed -i '' 's/import javax\./import jakarta./g' {} +
