import random as rd

def sample(p) :
    r = rd.uniform(0,1)
    tmp = 0
    for i in range(len(p)):
        tmp += p[i]
        if r <= tmp :
            return i

#on passe en parametre le network
def enchantillon_rejet(N):
    echantillon_valide=False
    #exemple : [ [Burglary,JohnCalls] , ['True','False']] nb : de meme tailles les 2 sous listes !
    while(echantillon_valide==False):
        echantillon=[[],[]]
        #on parcoure le réseau baysien
        for v in N.liste_var:
            #on regarde pour chaque variable ses parents tiré (si existe) avec leur valeur boleen
            #inter_parents_existant = list(set(v.parents) & set(echantillon[0]))

            bool_l_parent=[]
            for e in v.parents:
                index = echantillon[0].index(e)
                bool_l_parent.append(echantillon[1][index])
            #on fait un tirage entre True et False en fonction des parents tiré (si existe)
            tirage = sample([v.trouver_proba(True,bool_l_parent),v.trouver_proba(False,bool_l_parent)])

            if(tirage==0):
                tirage_bool=True
            if(tirage==1):
                tirage_bool=False

            #on rejette l'echantillon
            if(v in N.varibales_certaines() and (v,tirage_bool) not in N.varibales_certaines_b()):
                # On rejette l'echantillon car on a pas tiré en fonction des valeurs boolen des variables du sachant que
                echantillon_valide=False
                break

            if(v in N.varibales_certaines() and (v,tirage_bool) in N.varibales_certaines_b()):
                #on valide échantillon et on le rajoute car bonne valeur boolen des variables du sachant que
                echantillon[0].append(v)
                echantillon[1].append(str(tirage_bool).lower())
                echantillon_valide=True

            if(v not in N.varibales_certaines()):
                #on valide échantillon et on le rajoute
                echantillon[0].append(v)
                echantillon[1].append(str(tirage_bool).lower())
                echantillon_valide=True

        if (echantillon_valide):
            return echantillon

def prob_rej(rb, it):
    occu = []
    var = None
    for e in rb.liste_var:
        if e in rb.prob[0]:
            var = e
            occu = [0] * len(var.value)

    #on compte les occurences de chacune des valeurs dans les echantillons tires
    for i in range(it):
        ech = enchantillon_rejet(rb)
        occu[var.value.index(ech[1][ech[0].index(var)])] += 1

    proba = list(map(lambda x: x/it, occu))
    return proba
