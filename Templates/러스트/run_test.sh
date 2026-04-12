#!/bin/bash
TEST_DIR="resources"

FILES=$(find ${TEST_DIR} -type f -name "*.in" -exec basename {} \;)

cargo build

CARGO_EXIT_CODE=$?

echo "cargo exit code : " ${CARGO_EXIT_CODE}

if [ $CARGO_EXIT_CODE -eq 0 ]; then
    echo "compile success"
    for FILE in $FILES ; do
        echo "file name : ${FILE}"
        cat $TEST_DIR/$FILE;
        # FILE에서 확장자를 제거한 이름을 추출한다
        # 예: 1.in -> 1
        FILE_NAME=$(echo $FILE | sed 's/\.in$//')
        OUTPUT_FILE_NAME=${TEST_DIR}/${FILE_NAME}.out
        echo "";
        # OUTPUT_FILE을 읽어서 문자열을 저장한다. (끝의 공백을 trim)
        EXPECTED_OUTPUT=$(perl -0777 -pe 's/\s+$//' "$OUTPUT_FILE_NAME")
        # 실제OUTPUT (끝의 공백을 trim)
        ACTUAL_OUTPUT=$(cat "$TEST_DIR/$FILE" | ./target/debug/Rust | perl -0777 -pe 's/\s+$//')

        ## OUTPUT_FILE의 문자열과 ACTUAL_OUTPUT의 문자열을 비교한다. 마지막 줄바꿈은 제외한다.
        if [ "$EXPECTED_OUTPUT" == "$ACTUAL_OUTPUT" ]; then
            echo "Test ${FILE_NAME} passed.";
        else
            echo "Test ${FILE_NAME} failed.";
            echo "Expected: ${EXPECTED_OUTPUT}";
            echo "Actual: ${ACTUAL_OUTPUT}";
            exit -1;
        fi

    done;
else
    echo "Cargo build failed.";
    exit -1;
fi

