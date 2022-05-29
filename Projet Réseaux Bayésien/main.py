from VN import Variable
from VN import Network
from rejet import *
from ws import *
from gibbs import *
import matplotlib.pyplot as plt
import time

def lireBD(nom_bd):
    bd = open(nom_bd, "r")
    line = bd.readline().lower()
    rb = []

    while (line != ""):
        lineTab = line.translate(str.maketrans("{}[](),;|=\n", "           ")).split()

        if lineTab != []:
            if lineTab[0] == "network":
                rb = rb + [Network(lineTab[1])]

            elif lineTab[0] == "variable":
                line = bd.readline().lower()
                arg = line.translate(str.maketrans("{}[](),;|=\n", "           ")).split()

                var = Variable(lineTab[1], arg[1:])
                rb[len(rb)-1].addVar(var)

            elif lineTab[0] == "probability":
                dep = lineTab[1:]

                arg = []
                lineTab = [None]
                while lineTab != []:
                    line = bd.readline().lower()
                    lineTab = line.translate(str.maketrans("{}[](),;|=\n", "           ")).split()
                    if lineTab != []:
                        arg = arg + [lineTab]

                rb[len(rb)-1].addP(dep, arg)

            elif lineTab[0] == "prob":
                rb[len(rb)-1].addProb(lineTab[1:])

            else:
                print("Erreur de syntaxe dans la base de donnée")

        line = bd.readline().lower()
    bd.close()
    return rb

def comp_avec_rej(list_rb):
    err_rej = []
    exe_rej = []
    err_ws = []
    exe_ws = []
    err_gib = []
    exe_gib = []

    it = list(map(lambda x: (x + 1) * 1000, range(10)))

    for i in it:
        print(i/100-10, "%")

        res = err_exe_rej(list_rb, i)
        err_rej = err_rej + [res[0]]
        exe_rej = exe_rej + [res[1]]

        res = err_exe_ws(list_rb, i)
        err_ws = err_ws + [res[0]]
        exe_ws = exe_ws + [res[1]]

        res = err_exe_gib(list_rb, i)
        err_gib = err_gib + [res[0]]
        exe_gib = exe_gib + [res[1]]

    print("100.0 %")

    #on affiche les graphes respectivement pour l'erreur et pour le temps d'execution
    plt.plot(it, err_rej)
    plt.plot(it, err_ws)
    plt.plot(it, err_gib)
    plt.legend(["Rejet", "Weighted sampling", "Gibbs"])
    plt.xlabel("nb echantillon")
    plt.ylabel("erreur moyenne")
    plt.title("Erreur moyenne en fonction du nombre d'echantillons")
    plt.savefig("graphe\erreur_avec_rejet")
    plt.clf()

    plt.plot(it, exe_rej)
    plt.plot(it, exe_ws)
    plt.plot(it, exe_gib)
    plt.legend(["Rejet", "Weighted sampling", "Gibbs"])
    plt.xlabel("nb echantillon")
    plt.ylabel("temps moyen")
    plt.title("Temps moyen en fonction du nombre d'echantillons")
    plt.savefig("graphe\\temps_avec_rejet")
    plt.clf()

    print("Les graphiques avec rejet ont été générés et sauvegardés !\n")

def comp_sans_rej(list_rb):
    err_ws = []
    exe_ws = []
    err_gib = []
    exe_gib = []

    it = list(map(lambda x: (x + 1) * 10000, range(10)))

    for i in it:
        print(i/1000-10, "%")

        res = err_exe_ws(list_rb, i)
        err_ws = err_ws + [res[0]]
        exe_ws = exe_ws + [res[1]]

        res = err_exe_gib(list_rb, i)
        err_gib = err_gib + [res[0]]
        exe_gib = exe_gib + [res[1]]

    print("100.0 %")

    #on affiche les graphes respectivement pour l'erreur et pour le temps d'execution
    plt.plot(it, err_ws)
    plt.plot(it, err_gib)
    plt.legend(["Weighted sampling", "Gibbs"])
    plt.xlabel("nb echantillon")
    plt.ylabel("erreur moyenne")
    plt.title("Erreur moyenne en fonction du nombre d'echantillons")
    plt.savefig("graphe\erreur_sans_rejet")
    plt.clf()

    plt.plot(it, exe_ws)
    plt.plot(it, exe_gib)
    plt.legend(["Weighted sampling", "Gibbs"])
    plt.xlabel("nb echantillon")
    plt.ylabel("temps moyen")
    plt.title("Temps moyen en fonction du nombre d'echantillons")
    plt.savefig("graphe\\temps_sans_rejet")
    plt.clf()

    print("Les graphiques sans rejet ont été générés et sauvegardés !\n")

def err_exe_rej(list_rb, it):
    #premier de chaque liste est la somme des erreurs et le second la somme des temsp d'execution
    res = [0, 0]

    for rb in list_rb:
        start = time.time()
        sol = prob_rej(rb, it)
        end = time.time()
        res[0] += abs(float(rb.prob[1][0]) - sol[0])
        res[1] += end - start

    #on divise par le nb de rb dans la liste pour avoir les moyennes
    res = list(map(lambda x: x/len(list_rb), res))
    return res

def err_exe_ws(list_rb, it):
    #premier de chaque liste est la somme des erreurs et le second la somme des temsp d'execution
    res = [0, 0]

    for rb in list_rb:
        start = time.time()
        sol = prob_ws(rb, it)
        end = time.time()
        res[0] += abs(float(rb.prob[1][0]) - sol[0])
        res[1] += end - start

    #on divise par le nb de rb dans la liste pour avoir les moyennes
    res = list(map(lambda x: x/len(list_rb), res))
    return res

def err_exe_gib(list_rb, it):
    #premier de chaque liste est la somme des erreurs et le second la somme des temsp d'execution
    res = [0, 0]
    for rb in list_rb:
        start = time.time()
        sol = prob_gib(rb, it)
        end = time.time()
        res[0] += abs(float(rb.prob[1][0]) - sol[0])
        res[1] += end - start

    #on divise par le nb de rb dans la liste pour avoir les moyennes
    res = list(map(lambda x: x/len(list_rb), res))
    return res


menu = True
while menu:
    print("Que voulez-vous faire ?")
    print("\t 0 : Approximer les probabilités d'un réseau bayésien binaire")
    print("\t 1 : Calculer l'erreur moyenne et le temps moyen d'exécution")
    print("\t 2 : Quitter")

    choix = int(input("Entrez 0, 1 ou 2 : "))
    while choix not in [0, 1, 2]:
        choix = int(input("Entrez 0, 1 ou 2 : "))

    if choix==0:
        print("\nEntrez le nom du fichier contenant le réseau bayésien binaire : ")
        fichier = (input(""))
        rb = lireBD(fichier)

        print("\nQuelle méthode voulre-vous utiliser ?")
        print("\t 0 : Rejet")
        print("\t 1 : Weighted sampling")
        print("\t 2 : Gibbs")

        choix_methode = int(input("Entrez 0, 1 ou 2 : "))
        while choix_methode not in [0, 1, 2]:
            choix_methode = int(input("Entrez 0, 1 ou 2 : "))

        it = int(input("\nNombre d'échantillon : "))

        if choix_methode == 0:
            start = time.time()
            res = prob_rej(rb[0], it)
            end = time.time()

        if choix_methode == 1:
            start = time.time()
            res = prob_ws(rb[0], it)
            end = time.time()

        if choix_methode == 2:
            start = time.time()
            res = prob_gib(rb[0], it)
            end = time.time()

        print("Erreur : ", abs(float(rb[0].prob[1][0]) - res[0]))
        print("Temps : ", end - start)
        print()

    if choix==1:
        print("\nEntrez le nom du fichier contenant les réseaux bayésiens binaires : ")
        fichier = input("")
        rb = lireBD(fichier)

        print("\nVoulez-vous utilisez le rejet (en plus du weighted sampling et de gibbs) ?")
        print("\t 0 : Avec rejet (Moyenne effectuée sur moins d'échantillons)")
        print("\t 1 : Sans rejet")

        choix_methode = int(input("Entrez 0 ou 1 : "))
        while choix_methode not in [0, 1]:
            choix_methode = int(input("Entrez 0 ou 1 : "))
        print()

        if choix_methode==0:
            comp_avec_rej(rb)

        if choix_methode==1:
            comp_sans_rej(rb)

    if choix==2:
        menu = False
