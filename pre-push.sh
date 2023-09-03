#!/bin/bash

# Building and Testing
echo "Starting build and test process..."
mvn clean install
if [ $? -ne 0 ]; then
    echo "Build or tests failed!"
    exit 1
fi
echo "Build and tests completed successfully."

echo "All checks passed! You're good to push."
