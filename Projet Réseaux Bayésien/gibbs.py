from rejet import*

def enchantillon_gibbs(N,nb):
    # pense à verifier pour l etat de la target avant le sachant que
    target = N.target()
    #print(target)
    echantillon=[[],[]] # premier 0 : nb de target true et le deuxième nb target faux
    compte=[0,0]
    #Initialisation
    for t in N.varibales_certaines_b():
        echantillon[0] = echantillon[0] + [t[0]]
        echantillon[1] = echantillon[1] + [str(t[1]).lower()]
    for t in N.liste_var:
        if t in N.varibales_certaines():
            continue
        else:
            echantillon[0] = echantillon[0] + [t]
            echantillon[1] = echantillon[1] + ['true']
    for i in range(nb):
        for v in N.liste_var:
            if v in N.varibales_certaines():
                continue
            p_v_vrai = N.proba_v_couverture_markov(v,True,echantillon)
            p_v_faux = N.proba_v_couverture_markov(v,False,echantillon)
            p_vv_vrai = p_v_vrai / ( p_v_vrai + p_v_faux )
            p_vv_faux = p_v_faux / ( p_v_vrai + p_v_faux )
            S=p_vv_vrai + p_vv_faux
            if (S != 1):        #parce que S = 0,9999999999
                p_vv_vrai = 1 - p_vv_faux
            tirage = sample([p_vv_vrai,p_vv_faux])
            if(tirage==0):
                 tirage_bool='true'
            if(tirage==1):
                tirage_bool='false'
            if(tirage!=1 and tirage!=0):
                print('ERREUR')
            index = echantillon[0].index(v)
            echantillon[1][index] = tirage_bool
            index = echantillon[0].index(target)
            booll = echantillon[1][index]
            if booll == 'true':
                compte[0]=compte[0]+1
            if booll == 'false':
                compte[1]=compte[1]+1
    somme = compte[0] + compte[1]
    compte[0]=compte[0]/somme
    compte[1]=compte[1]/somme
    return compte

def prob_gib(rb, it):
    return enchantillon_gibbs(rb,it)
