if [ -d "./bin/" ]; then
    rm -r ./bin/*
fi
javac -d ./bin/ $(find ./src/tubes2tbfo/*.java | grep .java)