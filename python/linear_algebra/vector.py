import operator
from functools import reduce
import math
from decimal import Decimal, getcontext

class Vector(object):
    
    def __init__(self, coordinates):
        try:
            if not coordinates:
                raise ValueError
            self.coordinates = tuple([Decimal(x) for x in coordinates])
            self.dimension = len(coordinates)
        except ValueError:
            raise ValueError('The coordinates must be nonempty')
        except TypeError:
            raise TypeError('The coordinates must be an iterable')
            
    def __str__(self):
        return 'Vector: {}'.format(tuple(round(x,3) for x in self.coordinates))
    
    def __eq__(self, v):
        return self.coordinates == v.coordinates
    
    def __add__(self, v):
        return Vector(tuple(map(operator.add, self.coordinates, v.coordinates)))
    
    def __sub__(self, v):
        return Vector(tuple(map(operator.sub, self.coordinates, v.coordinates)))
    
    def __mul__(self, v):
        return Vector(tuple(map((Decimal(v)).__mul__, self.coordinates)))
    
    def magnitude(self):
        return Decimal(math.pow(reduce(lambda x,y: x + math.pow(y,2), self.coordinates, 0), 0.5))
    
    def normalized(self):
        mag = self.magnitude()
        if (mag == 0):
            raise Exception('Cannot normalize the zero vector')
        return self * (Decimal(1.0) / mag)
    
    def dot(self, v):
        if (v.dimension != self.dimension):
            raise Exception('Unequal dimensions {0} & {1}'.format(self.dimension, v.dimension))
        return reduce(lambda x, y: x + y, [x * y for (x,y) in zip(self.coordinates, v.coordinates)], 0)
    
    def cosAngle(self, v):
        dotproduct = self.dot(v)
        selfmag = self.magnitude()
        othermag = v.magnitude()
        # print(type(dotproduct), type(selfmag), type(othermag))
        try:
            return dotproduct / (selfmag * othermag)
        except ZeroDivisionError:
            raise Exception('Cannot get angle with zero vector')
            
    def angleRadians(self, v):
        return math.acos(self.cosAngle(v))
    
    def angleDegrees(self, v):
        return self.angleRadians(v) * 180 / math.pi
    
    def is_zero(self, tolerance = 1e-10):
        return self.magnitude() < tolerance
    
    def isParallel(self, v, tolerance = 1e-10):
        if (self.is_zero() or v.is_zero()):
            return True
        absangle = abs(self.cosAngle(v))
        # print('absangle = {}'.format(absangle))
        return abs(absangle - 1) < tolerance
    
    def isOrthogonal(self, v, tolerance = 1e-10):
        dotproduct = self.dot(v)
        # print('dot product is {}'.format(dotproduct))
        return abs(dotproduct) < tolerance
    
    def component_parallel_to(self, basis):
        '''
        Project this vector onto v (v is the basis vector) and get the parallel component
        '''
        if (basis.is_zero()):
            raise Exception('Cannot project to zero vector')
        norm = basis.normalized()
        return norm * self.dot(norm)
    
    def component_orthogonal_to(self, basis):
        return self - self.component_parallel_to(basis)

    def cross_product(self, v):
        '''
        cross product of v and w = (magnitude of v) * (magnitude of w) * (sin(theta))
        '''
        # sinAngle = math.sqrt(1 - math.pow(self.cosAngle(v), 2))
        # weight = self.magnitude() * v.magnitude()
        # mag_cross = weigth * sinAngle
        if (self.dimension != 3 and v.dimension != 3):
            raise Exception("You can only do cross product of 3 dimensional vectors")
        return Vector([
                self.coordinates[1] * v.coordinates[2] - v.coordinates[1] * self.coordinates[2],
                - (self.coordinates[0] * v.coordinates[2] - v.coordinates[0] * self.coordinates[2]),
                self.coordinates[0] * v.coordinates[1] - v.coordinates[0] * self.coordinates[1],
                      ])   
    def area_parallelogram(self, v):
        cp = self.cross_product(v)
        return cp.magnitude()
    
    def area_triangle(self, v):
        cp = self.cross_product(v)
        return Decimal(0.5) * cp.magnitude()
		
    def __iter__(self):
        for x in self.coordinates:
            yield x
	
    def __getitem__(self, key):
        return self.coordinates[key]

    def __setitem__(self, key, value):
        self.coordinates[key] = value
        
def print3dec(val):
    print(round(val, 3))
