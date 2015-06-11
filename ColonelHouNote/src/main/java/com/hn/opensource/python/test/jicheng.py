class SchoolMember:
    '''Represents any schoolmember'''
    def __init__(self, name, age):
        self.name = name
        self.age = age
        print '(Init SchoolMember:%s)'%self.name
    def tell(self):
        '''Tell my details'''
        print 'Name : "%s"Age : "%s"'%(self.name, self.age)
class Teacher(SchoolMember):
    '''Represents a teacher.'''
    def __init__(self, name, age, salary):
        SchoolMember.__init__(self, name, age)
        self.salary = salary
        print '(Init Teacher:%s)'%self.name
    def tell(self):
        SchoolMember.tell(self)
        print 'Salary:"%d"'%self.salary
class Student(SchoolMember):
    '''Represent a student'''
    def __init__(self, name, age, marks):
        SchoolMember.__init__(self, name, age)
        self.marks = marks
        print '(Init Student:%s)' %self.name
    def tell(self):
        SchoolMember.tell(self)
        print 'Marks  : %d' %self.marks

if __name__ == "__main__" :
    t = Teacher('Mrs Tom', 45, 8000)
    s = Student('Jim', 22, 78)
    mems = [t, s]
    for mem in mems:
        mem.tell()
