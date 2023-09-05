#/bin/bash

g++ -o main main.cpp --std=c++17;

answers=(2 2 1 5 9 0 1 4 2 26)

#for answer in ${answers[@]}; do
#    echo $answer;
#done

INDEX=0

for test_file in ./test*.txt; do
    #echo $test_file;
    result=$(cat $test_file | ./main);
    answer=${answers[$INDEX]};

    if [ $result = $answer ];
    then
        echo "$test_file success.";
    else
        echo "$test_file failed.";
        echo "expected : $answer but $result";
    fi
    INDEX=$(($INDEX+1))
done
