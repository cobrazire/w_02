from sys import argv

given = False
event = []
obs = []
for i in range(1,len(argv)):
    if argv[i] == "given":
        given = True
        continue
    event.append(argv[i])
    if given:
        obs.append(argv[i])

class BayesNode(object):
    def prob(self, B, E, A, J, M):
        result = (self.P("B",B,None,None) * self.P("E",E,None,None) * self.P("A|B,E",A,B,E) * self.P("J|A",J,A,None) * self.P("M|A",M,A,None))
        return result

    def P(self,event,val1,val2,val3):
        if event == "B":
            if val1:
                return 0.001
            else:
                return 0.999

        if event == "E":
            if val1:
                return 0.002
            else:
                return 0.998

        if event == "A|B,E":
            if val2 and val3:
                var = 0.95
            if val2 and not val3:
                var = 0.94
            if not val2 and val3:
                var = 0.29
            if not val2 and not val3:
                var = 0.001
            if val1:
                return var
            else:
                return (1-var)

        if event == "J|A":
            if val2:
                var = 0.9
            else:
                var = 0.05
            if val1:
                return var
            else:
                return (1-var)

        if event == "M|A":
            if val2:
                var = 0.7
            else:
                var = 0.01
            if val1:
                return var
            else:
                return (1-var)

    def enum(self, variables):
        if not None in variables:
            return self.prob(variables[0],variables[1],variables[2],variables[3],variables[4])
        else:
            index_none = variables.index(None)
            new_var = list(variables)
            new_var[index_none] = True
            value1 = self.enum(new_var)
            new_var[index_none] = False
            value2 = self.enum(new_var)
            return value1 + value2

    def Values(self,variables):
        result = []
        if "Bt" in variables:
            result.append(True)
        elif "Bf" in variables:
            result.append(False)
        else:
            result.append(None)
        if "Et" in variables:
            result.append(True)
        elif "Ef" in variables:
            result.append(False)
        else:
            result.append(None)
        if "At" in variables:
            result.append(True)
        elif "Af" in variables:
            result.append(False)
        else:
            result.append(None)
        if "Jt" in variables:
            result.append(True)
        elif "Jf" in variables:
            result.append(False)
        else:
            result.append(None)
        if "Mt" in variables:
            result.append(True)
        elif "Mf" in variables:
            result.append(False)
        else:
            result.append(None)

        return result

bnet = BayesNode()

if event:
    numeric = bnet.enum(bnet.Values(event))
    if obs:
        num_val = bnet.enum(bnet.Values(obs))
    else:
        num_val = 1
    print("Probability : %.9f" % (numeric/num_val))

else:
    print("Invalid event string")
