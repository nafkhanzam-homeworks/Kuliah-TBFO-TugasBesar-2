if [ -d "./bin/" ]; then
    rm -r ./bin/*
fi
javac -d ./bin/ $(find ./* | grep .java)