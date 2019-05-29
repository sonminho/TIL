s1 = set([1,2,3,4,5,6])
s2 = set([4,5,6,7,8,9])

# 교집합
print(s1 & s2)
print(s1.intersection(s2))
print()

# 합집합
print(s1 | s2)
print(s1.union(s2))
print()

# 차집합
print(s1 - s2)
print(s1.difference(s2))
print(s2.difference(s1))
print()