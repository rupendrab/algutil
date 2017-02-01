from vector import Vector
from decimal import Decimal, getcontext

getcontext().prec = 30

class Line(object):
    
    NO_NONZERO_ELTS_FOUND_MSG = "No nonzero elements found"
    
    def __init__(self, normal_vector=None, constant_term=None):
        self.dimension = 2
        
        if not normal_vector:
            all_zeors = ['0'] * self.dimension
            normal_vector = Vector(all_zeors)
        self.normal_vector = normal_vector
        
        if not constant_term:
            constant_term = Decimal('0')
        self.constant_term = Decimal(constant_term)
        
        self.set_basepoint()

    def set_basepoint(self):
        try:
            n = self.normal_vector
            c = self.constant_term
            basepoint_coords = ['0'] * self.dimension
            
            initial_index = Line.first_nonzero_index(n)
            initial_coefficient = n[initial_index]
            
            basepoint_coords[initial_index] = c / initial_coefficient
            self.basepoint = Vector(basepoint_coords)
        except Exception as e:
            if str(e) == Line.NO_NONZERO_ELTS_FOUND_MSG:
                self.basepoint = None
            else:
                raise e
                
    def __str__(self):
        num_decimal_places = 3
        
        def write_coefficient(coefficient, is_initial_term=False):
            coefficient = round(coefficient, num_decimal_places)
            if coefficient % 1 == 0:
                coefficient = int(coefficient)
            output = ''
            
            if coefficient < 0:
                output += '-'
            if coefficient > 0 and not is_initial_term:
                output += '+'
            if not is_initial_term:
                output += ' '
            if abs(coefficient) != 1:
                output += '{}'.format(abs(coefficient))
                
            return output
        
        n = self.normal_vector
        try:
            initial_index = Line.first_nonzero_index(n)
            terms = [
                write_coefficient(n[i], is_initial_term=(i==initial_index)) + 'x_{}'.format(i+1)
                for i in range(self.dimension) if round(n[i], num_decimal_places) != 0
                    ]
            output = ' '.join(terms)
            
        except Exception as e:
            if str(e) == Line.NO_NONZERO_ELTS_FOUND_MSG:
                output = '0'
            else:
                raise e
                
        constant = round(self.constant_term, num_decimal_places)
        if constant % 1 == 0:
            constant = int(constant)
        output += ' = {}'.format(constant)
        
        return output
    
    def is_parallel(self, l):
        return self.normal_vector.isParallel(l.normal_vector)
    
    def is_same(self, l):
        if self.normal_vector.is_zero():
            if not l.normal_vector.is_zero():
                return False
            else:
                diff = self.constant_term - l.constant_term
                return MyDecimal(diff).is_near_zero()
        elif l.normal_vector.is_zero():
            return False
            
        if not self.is_parallel(l):
            return False
        v1 = self.basepoint
        v2 = l.basepoint
        v = v1 - v2
        return (v.isOrthogonal(self.normal_vector) and v.isOrthogonal(l.normal_vector))
        
    def intersection(self, l):
        p = self.is_parallel(l)
        s = self.is_same(l)
        x = None
        y = None
        A = self.normal_vector[0]
        B = self.normal_vector[1]
        C = l.normal_vector[0]
        D = l.normal_vector[1]
        k1 = self.constant_term
        k2 = l.constant_term
        if not p:
            denom = A * D - B * C
            x = (D * k1 - B * k2) / denom
            y = (-C * k1 + A * k2) / denom
            x = float(x)
            y = float(y)
            return {'Point': Vector([x,y]), 'Parallel': p, 'Same': s}
        else:
            return {'Point': None, 'Parallel': p, 'Same': s}
    
    @staticmethod
    def first_nonzero_index(iterable):
        for k, item in enumerate(iterable):
            if not MyDecimal(item).is_near_zero():
                return k
        raise Exception(Line.NO_NONZERO_ELTS_FOUND_MSG)
        
class MyDecimal(Decimal):
    def is_near_zero(self, eps=10e-10):
        return abs(self) < eps
