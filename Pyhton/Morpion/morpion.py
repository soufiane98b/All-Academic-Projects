import random as rd
def affichePlateau(n,T):
	x='--'
	y=' |'
	print(n*x+'-')
	for i in range(n):
		for j in range(n):
			print('|'+str(T[i][j]),end='')
		print('|')
		print(n*x+'-')

def coupJoueur(n,T):
	x=int(input('Numero de ligne : '))
	y=int(input('Numero de colonne : '))
	while x<1 or x>n or y<1 or y>n or T[x-1][y-1]!=' ':
		print('choisir une autre cause car remplie déja')
		x=int(input('Numero de ligne : '))
		y=int(input('Numero de colonne : '))
	T[x-1][y-1]='X'
	return x-1,y-1

def coupOrdinateurAlea(n,T):
	x=rd.randint(0,n-1)
	y=rd.randint(0,n-1)
	while T[x][y]!=' ':
		x=rd.randint(0,n-1)
		y=rd.randint(0,n-1)
	T[x][y]='O'
	return x,y

def gagneEnLigne(n,T,l,sym):
	for v in T[l]:
		if v!=sym:
			return False
	return True 

def gagneEnColonne(n,T,c,sym):
	for i in range(n):
		if T[i][c]!=sym:
			return False
	return True

def gagneEnDiagonale(n,T,sym):
	c1=True
	c2=True
	for i in range(n):
		if T[i][i]!=sym:
			c1=False
		if T[i][n-i-1]!=sym:
			c2=False
	if c1==False and c2==False:
		return False
	return True

def gagne(n,T,x,y,sym):
	return gagneEnLigne(n,T,x,sym) or gagneEnColonne(n,T,y,sym) or gagneEnDiagonale(n,T,sym)

def creerPlateau(n):
	T=[]
	for i in range(n):
		T.append([])
	for v in T:
		for i in range(n):
			v.append(' ')
	return T


def Tabplein(T):
	for v in T:
		for u in v:
			if u==' ':
				return False
	return True 




def partieAlea(n,T):
	affichePlateau(n,T)
	while Tabplein(T)!=True:
		print('A vous de jouer et de choisir une case :')
		xj,yj=coupJoueur(n,T)
		affichePlateau(n,T)
		if gagne(n,T,xj,yj,'X')==True:
			return'vous avez gagné'
		xo,yo=coupOrdinateurAlea(n,T)
		print("Coup joué par l'ordinateur : ",str(xo+1)+','+str(yo+1))
		affichePlateau(n,T)
		if gagne(n,T,xo,yo,'O')==True:
			return'vous avez perdu'
	return 'Match nul'


#code pour ordi optimisée

def enLigne(n,T,l,sym):# l entre 0 et n-1
	if T[l].count(sym)==n-1 and T[l].count(' ')==1:
		return l,T[l].index(' ')
	else:
		return -1,-1

def enColonne(n,T,c,sym):# c entre 0 et n-1
	g=[]
	for i in range(n):
		g.append(T[i][c])
	if g.count(sym)==n-1 and g.count(' ')==1:
		return g.index(' '),c
	return -1,-1

def enDiagonale(n,T,sym):
	g=[]
	m=[]
	for i in range(n):
		g.append(T[i][i])
	if g.count(sym)==n-1 and g.count(' ')==1:
		return g.index(' '),g.index(' ')
	for i in range(n):
		m.append(T[i][n-1-i])
	if m.count(sym)==n-1 and m.count(' ')==1:
		return m.index(' '),n-1-m.index(' ')
	return -1,-1


def coupOrdinateurOptimise(n,T):
	for i in range(n): 
		if enLigne(n,T,i,'O') != (-1,-1):
			x,y=enLigne(n,T,i,'O')
			T[x][y]='O'
			return x,y
		if enColonne(n,T,i,'O') != (-1,-1):
			x,y=enColonne(n,T,i,'O')
			T[x][y]='O'
			return x,y
	if enDiagonale(n,T,'O')!= (-1,-1):
		x,y=enDiagonale(n,T,'O')
		T[x][y]='O'
		return x,y
	for i in range(n): 
		if enLigne(n,T,i,'X') != (-1,-1):
			x,y=enLigne(n,T,i,'X')
			T[x][y]='O'
			return x,y
		if enColonne(n,T,i,'X') != (-1,-1):
			x,y=enColonne(n,T,i,'X')
			T[x][y]='O'
			return x,y
	if enDiagonale(n,T,'X')!= (-1,-1):
		x,y=enDiagonale(n,T,'X')
		T[x][y]='O'
		return x,y
	return coupOrdinateurAlea(n,T)



def partieOptimisee(n,T):
	affichePlateau(n,T)
	while Tabplein(T)!=True:
		print('A vous de jouer et de choisir une case :')
		xj,yj=coupJoueur(n,T)
		affichePlateau(n,T)
		if gagne(n,T,xj,yj,'X')==True:
			return'vous avez gagné'
		if Tabplein(T)==True:
			break
		xo,yo=coupOrdinateurOptimise(n,T)
		print("Coup joué par l'ordinateur : ",str(xo+1)+','+str(yo+1))
		affichePlateau(n,T)
		if gagne(n,T,xo,yo,'O')==True:
			return'vous avez perdu'
		if Tabplein(T)==True:
			break
	return 'Match nul'

#Pour 2 joueur:

def coupJoueur2(n,T):
	x=int(input('Numero de ligne : '))
	y=int(input('Numero de colonne : '))
	while x<1 or x>n or y<1 or y>n or T[x-1][y-1]!=' ':
		print('choisir une autre cause car remplie déja')
		x=int(input('Numero de ligne : '))
		y=int(input('Numero de colonne : '))
	T[x-1][y-1]='O'
	return x-1,y-1

def partie2joueurs(n,T):
	j1=input('nom joueur 1 ')
	j2=input('nom joueur 2 ')
	affichePlateau(n,T)
	while Tabplein(T)!=True:
		print('A toi de jouer '+j1+' et de choisir une case : ')
		x1,y1=coupJoueur(n,T)
		affichePlateau(n,T)
		if gagne(n,T,x1,y1,'X')==True:
			return'vous avez gagné '+j1
		elif Tabplein(T)==True:
			break
		print('A toi de jouer '+j2+' et de choisir une case : ')
		x2,y2=coupJoueur2(n,T)
		affichePlateau(n,T)
		if gagne(n,T,x2,y2,'O')==True:
			return'vous avez gagné '+j2
		if Tabplein(T)==True:
			break
	return 'Match nul'

print("quelle est la taille du plateau", end=" ")
ch = input()
tp = int(ch)
print("ecrivez 0 si vous souhaiter jouer contre l'ordinateur sinon 1 pour jouer contre une autre persone", end=" ")
sh=input()
choix_adversaire=int(sh)
if choix_adversaire==0:
	T=creerPlateau(tp)
	partieOptimisee(tp,T)
if choix_adversaire==1:
	T=creerPlateau(tp)
	partie2joueurs(tp,T)
