from rejet import*

def enchantillon_weighted_sampling(N):
    # exemple : [ [Burglary,JohnCalls] , ['True','False'] , poids] nb : de meme tailles les 2 sous listes !
    echantillon=[[],[],1]
    # On parcoure le réseau baysien
    for v in N.liste_var:
        # on regarde pour chaque variable ses parents tiré ( si existe ) avec leur valeur boleen
        #inter_parents_existant = list(set(v.parents) & set(echantillon[0]))
        bool_l_parent=[]
        for e in v.parents:
            index = echantillon[0].index(e)
            bool_l_parent.append(echantillon[1][index])

        if(v in N.varibales_certaines()):
            index = N.varibales_certaines().index(v)
            bool_v = N.varibales_certaines_b()[index][1]
            echantillon[0].append(v)
            echantillon[1].append(str(bool_v).lower())
            p = v.trouver_proba(bool_v,bool_l_parent)
            echantillon[2] = echantillon[2]*p

        if(v not in N.varibales_certaines()):
            tirage = sample([v.trouver_proba(True,bool_l_parent),v.trouver_proba(False,bool_l_parent)])
            if(tirage==0):
                tirage_bool=True
            if(tirage==1):
                tirage_bool=False
            if(tirage!=1 and tirage!=0 ) :
                print('erreur')
                print(tirage)
                print(v)
                print(inter_parents_existant)
                print(bool_l_parent)
                print(v.trouver_proba(True,bool_l_parent))
                print(v.trouver_proba(False,bool_l_parent))
            echantillon[0].append(v)
            echantillon[1].append(str(tirage_bool).lower())

    return echantillon

def prob_ws(rb, it):
    poids = []
    var = None
    tot = 0
    for e in rb.liste_var:
        if e in rb.prob[0]:
            var = e
            occu = [0] * len(var.value)

    #on somme les poids de chaque valeur
    for i in range(it):
        ech = enchantillon_weighted_sampling(rb)
        occu[var.value.index(ech[1][ech[0].index(var)])] += ech[2]
        tot += ech[2]

    proba = list(map(lambda x: x/tot, occu))
    return proba
