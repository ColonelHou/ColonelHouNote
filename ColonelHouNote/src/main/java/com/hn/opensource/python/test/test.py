# -*- coding: utf-8 -*-
class Person:
    def __init__(self, name):#对象建立时马上执行 和Java中的 constructor
        self.name = name
    def sayHi(self):
        print 'Hello , How are you?', self.name


p = Person('tom')
print p
p.sayHi()
print '==============================================='
class Stu:
    '''represents a stu'''
    number = 0
    def __init__(self, name):
        '''init the stu's data'''
        self.name = name
        print '(Initializeds  %s)'%self.name
        Stu.number += 1
        print 'Init %d stu'%Stu.number
    def __del__(self):
        '''I am dying.'''
        print '%s saybye.'%self.name
        Stu.number -= 1
        if Stu.number == 0:
            print 'I am the last one'
        else:
            print 'There are still %d Stu left.'%Stu.number
    def sayHi(self):
        '''Greeting by the person'''
        print 'Hi , my name is %s'%self.name
    def howMany(self):
        '''Prints the current population.'''
        if Stu.number == 1:
            print 'I am the only stu here.'
        else:
            print 'We have %d persons here.'%Stu.number

st = Stu('Tom')
st.sayHi()
st.howMany()

tm = Stu('Jim')
tm.sayHi()
tm.howMany()

st.sayHi()
st.howMany()
print st.__doc__
print st.sayHi.__doc__
