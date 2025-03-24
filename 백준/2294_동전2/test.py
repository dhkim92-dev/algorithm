if __name__ == '__main__' :
    a =[2,5,7,1,3,4,8,6,9,3]
    a = sorted(a)
    print(a)

    s = 0
    left = 0
    right = len(a)-1
    while left < right :
        s += abs(a[left] - a[right]);
        left+=1
        right-=1

    print(s)

