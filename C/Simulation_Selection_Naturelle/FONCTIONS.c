//  FONCTIONS.c
//  Projet C L2 Selection Naturelle
//  Copyright © 2020 Programtion C. All rights reserved.
//  Crée par Soufiane Boustique et Nabil Mouzali le 11/12/2019.
//  faire en sorte que le fichier de sortie soit accesible 
#include "FONCTIONS.h"

// FONCTIONS GENERALES

// DEBUT ---------------------------------------------------------------------------------------------------------------------------------



// Fonction qui en entrée recoit une position x et y d'une cellule et sa direction entre 0 et 7 et modifie x et y pour qu 'elle indique cellule voisine(biensur 0<x<h et 0<y<l) nb: le monde est un tor

void cellule_voisine(int *posi_x,int *posi_y,int *direction,const int hauteur,const int largeur){
    switch(*direction){
        case 0: (*posi_x)++;break;
        case 1: (*posi_x)++;(*posi_y)++;break;
        case 2: (*posi_y)++;break;
        case 3: (*posi_x)--;(*posi_y)++;break;
        case 4: (*posi_x)--;break;
        case 5: (*posi_x)--;(*posi_y)--;break;
        case 6: (*posi_y)--;break;
        case 7: (*posi_x)++;(*posi_y)--;break;
    }
    if((*posi_x)==-1) (*posi_x)=hauteur-1;
    if((*posi_x)==hauteur) (*posi_x)=0;
    if((*posi_y)==-1) (*posi_y)=largeur-1;
    if((*posi_y)==largeur) (*posi_y)=0;
}

// Fonction qui permet de deplacer un animal en fonction du gene attribue , case et direction

void deplacer(int *posi_x,int *posi_y,int *direction,int *gene,const int hauteur,const int largeur){
    *direction=(*direction+*gene)%8;
    cellule_voisine(posi_x,posi_y,direction,hauteur,largeur);
    
}

//Fonction qui genere cellule aléatoirement en fonction de hauteur et largeur

void cellule_aleatoire(int *posi_x,int *posi_y,const int hauteur,const int largeur){
    *posi_x=rand()%hauteur;
    *posi_y=rand()%largeur;
    
    
}

// renvoi un gene aleatoire d'un chromosome
int gene_aleatoire(int chromosomes[]){
    int ch[8],i;
    for(i=0;i<8;i++){
        ch[i]=chromosomes[i];
    }
    int som=0,alea;
    for(i=0;i<8;i++){
        som=som+ch[i];
    }
    alea=rand()%som;
    int posi_gene_alea=0;
    while(alea-ch[posi_gene_alea]>=0){
        alea=alea-ch[posi_gene_alea];
        posi_gene_alea++;
    }
    if(posi_gene_alea<0 || posi_gene_alea>7){printf(" erreur dans posi_gene_alea");exit(0);}
    return chromosomes[posi_gene_alea];
}

void ajouter_nourriture_alea_monde(int **monde,int nourriture,const int hauteur,const int largeur){
    int x,y;
    cellule_aleatoire(&x,&y,hauteur,largeur);
    monde[x][y]=monde[x][y]+nourriture;
    
}

// ATTENTION SI hb==0 et lb==0 on va consider qu il n y a pas de beauce et on donc on rajoute rien
void ajouter_nourriture_alea_beauce(int **monde,int nourriture,const int hauteur,const int largeur,int coin_x_b,int coin_y_b,const int hauteur_b,const int largeur_b){
    //if(hauteur==hauteur_b && largeur==largeur_b){ajouter_nourriture_alea_monde(monde,nourriture,hauteur,largeur);return;}
    int x,y;
    cellule_aleatoire(&x,&y,hauteur_b,largeur_b);
    x=x+coin_x_b;
    y=y+coin_y_b;
    if(x>=hauteur) x=x-hauteur;
    if(y>=largeur) y=y-largeur;
    if(x>hauteur-1 || x<0 || y>largeur-1 || y<0){printf("erreur dans coordonnes beauce\n");exit(0);}
    monde[x][y]=monde[x][y]+nourriture;
    
}

void reajuster_beauce(const int hauteur,const int largeur,int *hauteur_b,int *largeur_b){
    if(*hauteur_b > hauteur){*hauteur_b=hauteur;}
    if(*largeur_b > largeur){*largeur_b=largeur;}
}

void centre_beauce(int *c_x,int *c_y,const int hauteur,const int largeur,int coin_x_b,int coin_y_b,const int hauteur_b,const int largeur_b){
    if(hauteur_b==0 && largeur_b==0){printf("beauce existe pas erreur");exit(0);}
    *c_x=(hauteur_b/2);
    *c_y=(largeur_b/2);
    if(hauteur==hauteur_b) coin_x_b=0;
    if(largeur==largeur_b) coin_y_b=0;// pour rendre uniforme car on travaille dans un sous rectangle du monde au complet
    *c_x=*c_x+coin_x_b;
    *c_y=*c_y+coin_y_b;
    if(*c_x>=hauteur) *c_x=*c_x-hauteur;
    if(*c_y>=largeur) *c_y=*c_y-largeur;
    if(*c_x>hauteur-1 || *c_x<0 || *c_y>largeur-1 || *c_y<0){printf("erreur des coordonnes centre beauce\n");exit(0);}
    //printf("centre beauce %d %d\n",*c_x,*c_y);
}

int distance_ani_du_beauce(animale *a,const int hauteur,const int largeur,int coin_x_b,int coin_y_b,const int hauteur_b,const int largeur_b){
    int c_x,c_y;
    centre_beauce(&c_x,&c_y,hauteur,largeur,coin_x_b,coin_y_b,hauteur_b,largeur_b);
    return max(abs(a->posi_x - c_x),abs(a->posi_y - c_y));
}

int **creer_monde(const int hauteur,const int largeur){
    int **tab2D=NULL;
    tab2D=(int **)malloc(hauteur*sizeof(int*));
    if(tab2D==NULL) exit(0);
    int i,j;
    for(i=0;i<hauteur;i++){
        *(tab2D+i)=NULL;
        *(tab2D+i)=(int *)malloc(largeur*sizeof(int));//=tab2D[i]
        if(tab2D[i]==NULL){ printf("erreur creation monde");return NULL;}
    }
    for(i=0;i<hauteur;i++){
        for(j=0;j<largeur;j++){
            tab2D[i][j]=0;
        }
    }
    return tab2D;
}


// on affiche le monde avec nourriture presente
void afficher_monde(int **monde,const int hauteur,const int largeur){
    int i,j;
    for(i=0;i<hauteur;i++){
        for(j=0;j<largeur;j++){
            printf("(%d) ",monde[i][j]);
        }
        printf("\n");
    }
}



// FIN ---------------------------------------------------------------------------------------------------------------------------------------------


// FONCTIONS ANIMALES

// DEBUT ---------------------------------------------------------------------------------------------------------------------------------------------

liste_animale *init_liste_animale(void){
    liste_animale *L=NULL;
    L=malloc(sizeof(liste_animale));
    if(L==NULL) {printf("erreur d'allocation 1");return NULL;}
    L->nb_animale=0;
    L->tete=NULL;
    return L;
    
}

// affiche dans l ordre inverse saisie

animale *creer_animale(int posi_x,int posi_y,int direction,int energie,int chromosome[8]){
    animale *a=NULL;
    a=malloc(sizeof(animale));
    if(a==NULL){printf("erreur d'allocation 2");return NULL;}
    a->posi_x=posi_x;
    a->posi_y=posi_y;
    a->direction=direction;
    a->energie=energie;
    int i;
    for(i=0;i<8;i++){
        a->chromosome[i]=chromosome[i];
    }
    a->suivant=NULL;
    return a;
}

void ajout_deb_liste_ani(liste_animale *L,int posi_x,int posi_y,int direction,int energie,int chromosome[8]){
    if(L==NULL){printf("erreur d'allocation 3");return;}
    animale *a=creer_animale(posi_x,posi_y,direction,energie,chromosome);
    if(a==NULL){printf("eruuuuuureureurue");return;}
    a->suivant=L->tete;
    L->tete=a;
}

void affichier_liste_ani(liste_animale *L){
    if(L==NULL){printf("erreur d'allocation 4");return;}
    animale *tmp=L->tete;
    if(tmp==NULL) {printf("liste vide  \n");return;}
    while(tmp!=NULL){
        printf("(%d %d) %d %d",tmp->posi_x,tmp->posi_y,tmp->direction,tmp->energie);
        printf(" | %d %d %d %d %d",tmp->chromosome[0],tmp->chromosome[1],tmp->chromosome[2],tmp->chromosome[3],tmp->chromosome[4]);
        printf(" %d %d %d |",tmp->chromosome[5],tmp->chromosome[6],tmp->chromosome[7]);
        printf(" %d\n",tmp->num_famille);
        tmp=tmp->suivant;
    }
}



void supprimer_liste_ani(liste_animale *L,animale *a){
    if(L==NULL || a==NULL){printf("liste vide ou animale==NULL");return ;}
     animale *tmp=L->tete;
     if(tmp==NULL){printf("liste vide : plus d'animaux dans le monde \n");return ;}
     
    
    if(tmp==a){L->tete=a->suivant;return ;}// ATTENTION ICI comme il y a pas de precedent
     while(tmp->suivant!=NULL && tmp->suivant!=a){
         tmp=tmp->suivant;
     }
     if(tmp->suivant==NULL){printf("animale n'exite pas dans la liste :\n");return ;}
     tmp->suivant=a->suivant;
    return ;
     
}

int dupliquer_ou_tuer_animale_liste(liste_animale *L, animale *a,int seuil_energie){
    // DUPLIQUER ANIMALE
    if(a->energie>=seuil_energie){
        a->energie=a->energie/2;
        int chromosome_mute[8]={0};
        int i,posi_gene,mutation;
        // duplication du chromosome
        for(i=0;i<8;i++){
            chromosome_mute[i]=a->chromosome[i];
        }
        posi_gene=rand()%8;
        mutation=rand()%3;
        mutation--;
        if((chromosome_mute[posi_gene]+mutation)>=1){chromosome_mute[posi_gene]=chromosome_mute[posi_gene]+mutation;}
        ajout_deb_liste_ani(L,a->posi_x,a->posi_y,a->direction,a->energie,chromosome_mute);
        L->tete->num_famille=a->num_famille;
        L->nb_animale++;
        return 0;
    }
    //Tuer aniamle
    if(a->energie==0){
        L->nb_animale--;
        supprimer_liste_ani(L,a);
        return 1;// renvoi celui d'avant si n est pas situe en tete de liste
    }
    // Sinon on fait rien on laisse la liste chainne comme elle est
    return 0;
}


void nourrir_et_baisser_energie_animale(animale *a,int **monde,int nourriture){
    if(monde[a->posi_x][a->posi_y]<0){printf("erreur energie negative dans le monde");exit(0);}
    if(monde[a->posi_x][a->posi_y]>=nourriture){
        a->energie=a->energie+nourriture;// ca veut dire qu il a au moin une nourriture sinon case contient 0 nourriture car marche avec multiple
        monde[a->posi_x][a->posi_y]=monde[a->posi_x][a->posi_y]-nourriture;}
    if(monde[a->posi_x][a->posi_y]<0){printf("erreur energie negative dans le monde");exit(0);}
    if(a->energie!=0 ) {a->energie--;}
}

void deplace_animale(animale *a,const int hauteur,const int largeur){
    if(a==NULL){printf("erreur deplacer animal");return;}
    int gene_alea;
    gene_alea=gene_aleatoire(a->chromosome);
    deplacer(&(a->posi_x),&(a->posi_y),&(a->direction),&gene_alea,hauteur,largeur);
}




// FIN ---------------------------------------------------------------------------------------------------------------------------------------------

// FONCTIONS SUR FICHIERS

// DEBUT ---------------------------------------------------------------------------------------------------------------------------------------------
int que_espace_retour_ligne_dieze(char *fin){
    if(fin[0]=='#'){return 1;}
    int i;
    for(i=0;fin[i]!='\0';i++){
        if(fin[i]==' ')continue;
        if(fin[i]=='#'){return 1;}
        if(fin[i]!='\n')return 0;
    }
    return 1;
}


void verifier_syntaxe_fichier(char nom_fichier[300]){
    FILE *fichier=NULL;
    fichier=fopen(nom_fichier,"r");
    if(fichier==NULL){printf("erreur ouverture fichier source 1");exit(0);}
    int a,b,c,d,ligne=1;
    char fin[500];
    //---------------------------
    if(fgetc(fichier)=='#'){fgets(fin,400,fichier);ligne++;}
    else{rewind(fichier);}
    //--------
    if(fscanf(fichier,"Monde %d %d",&a,&b)!=2){printf("erreur ecriture ligne %d \n",ligne);exit(0);}
    fgets(fin,400,fichier);
    if(que_espace_retour_ligne_dieze(fin)==0){printf("erreur ecriture ligne %d \n",ligne);exit(0);}
    ligne++;
    //--------
    if(fscanf(fichier,"Beauce %d %d %d %d",&a,&b,&c,&d)!=4){printf("erreur ecriture ligne %d \n",ligne);exit(0);}
    fgets(fin,400,fichier);
    if(que_espace_retour_ligne_dieze(fin)==0){printf("erreur ecriture ligne %d \n",ligne);exit(0);}
    ligne++;
    //--------
    if(fscanf(fichier,"Energie Nourriture %d",&a)!=1){printf("erreur ecriture ligne %d \n",ligne);exit(0);}
    fgets(fin,400,fichier);
    if(que_espace_retour_ligne_dieze(fin)==0){printf("erreur ecriture ligne %d \n",ligne);exit(0);}
    ligne++;
    //--------
    if(fscanf(fichier,"Seuil Reproduction %d",&a)!=1){printf("erreur ecriture ligne %d \n",ligne);exit(0);}
    fgets(fin,400,fichier);
    if(que_espace_retour_ligne_dieze(fin)==0){printf("erreur ecriture ligne %d \n",ligne);exit(0);}
    ligne++;
    //--------
    fgets(fin,400,fichier);
    if(que_espace_retour_ligne_dieze(fin)==0){printf("erreur ecriture ligne %d  oubli de saut de ligne\n",ligne);exit(0);}
    ligne++;
    //--------
    fgets(fin,400,fichier);
    if(que_espace_retour_ligne_dieze(fin)==0){printf("erreur ecriture ligne %d oubli de saut de ligne \n",ligne);exit(0);}
    //--------
    int c1,c2,c3,c4,c5,c6,c7,c8,test=0;
    do{
        ligne++;
        test=fscanf(fichier,"(%d %d) %d %d | %d %d %d %d %d %d %d %d |",&a,&b,&c,&d,&c1,&c2,&c3,&c4,&c5,&c6,&c7,&c8);
        fgets(fin,400,fichier);
        if(que_espace_retour_ligne_dieze(fin)==0){printf("erreur ecriture ligne %d \n",ligne);exit(0);}
        // ceci sert a lire le  fichier de sortie
        char m=fgetc(fichier);
        if(m=='\n' || m==EOF){fclose(fichier);return;}
        else{fseek(fichier,-1,SEEK_CUR);}
        //----------------------------------
    }
    while(test==12);
    if(test==EOF){fclose(fichier);return;}
    printf("erreur ecriture ligne %d \n",ligne);
    exit(0);
}

// fonction qui recupere toutes les donnes du fichier d entre et renvoi une liste animale remplie
//indique a l’utilisateur que le format du fichier est illisible ou incorrect si c’est le cas.
// FICHIER D ENTREE

void recupe_donnees_fichier(char nom_fichier[300],int *hauteur,int *largeur,int *csb_x,int *csb_y,int *hauteur_b,int *largeur_b,int *nourriture,int *seuil_energie,liste_animale *L){
    FILE *fichier=NULL;
    char ligne[500];
    char ch1[20],ch2[20],ch3[20];
    fichier=fopen(nom_fichier,"r");
    if(fichier !=NULL){
        int l=1;
        if(fgetc(fichier)=='#'){fgets(ligne,400,fichier);l++;}
        else{rewind(fichier);}
        //----------------------------------------------------------------------------------------------------------------------------------------
        fscanf(fichier,"%s%d%d",ch1,hauteur,largeur);fgets(ligne,400,fichier);
        if(*hauteur<0 || *largeur<0 ){printf("valeur hauteur ou largeur incorettre, erreur ligne : %d \n",l);exit(0);}
        l++;
        fscanf(fichier,"%s%d%d%d%d",ch1,csb_x,csb_y,hauteur_b,largeur_b);fgets(ligne,400,fichier);
        if(*csb_x>*hauteur-1 || *csb_y>*largeur-1 || *csb_x<0 || *csb_y<0 ){printf("valeur beauce incorettre, erreur ligne : %d \n",l);exit(0);}
        l++;
        fscanf(fichier,"%s%s%d",ch1,ch2,nourriture);fgets(ligne,400,fichier);
        if( *nourriture<0){printf("valeur de nourriture incorrecte, erreur ligne : %d \n",l);exit(0);}
        l++;
        fscanf(fichier,"%s%s%d",ch1,ch2,seuil_energie);fgets(ligne,400,fichier);
        if( *seuil_energie<0){printf("valeur seuil de reproduction incorrecte, erreur ligne : %d \n",l);exit(0);}
        //----------------------------------------------------------------------------------------------------------------------------------------
        fgets(ligne,400,fichier);
        fgets(ligne,400,fichier);
        //----------------------------------------------------------------------------------------------------------------------------------------
        int cmpt_ligne=7,num_famille=0,i;
        int chromosome[8];
        int posi_x,posi_y,direction,energie;
        
        // DEBUT LIGNE
        while(fscanf(fichier,"%c%d%d%c%d%d %c ",ch1,&posi_x,&posi_y,ch2,&direction,&energie,ch3)!=EOF){
            if(posi_x>*hauteur-1 || posi_y>*largeur-1 || posi_x<0 || posi_y<0 ) {
                printf("valeur de position incorecte, erreur ligne : %d \n",cmpt_ligne);exit(0);
            }
            if(direction<0 || direction>7 ) {
                printf("valeur de direction incorecte, erreur ligne : %d \n",cmpt_ligne);exit(0);
            }
            if(energie<0) {
                printf("valeur de energie incorecte, erreur ligne : %d \n",cmpt_ligne);exit(0);
            }
            for(i=0;i<8;i++){
                fscanf(fichier,"%d",&chromosome[i]);
                if(chromosome[i]<=0) {
                    printf("on arrette programme car valeur de un gene <=0 erreur ligne : %d \n",cmpt_ligne);exit(0);
                }
            }
            cmpt_ligne++;
            ajout_deb_liste_ani(L,posi_x,posi_y,direction,energie,chromosome);
            L->nb_animale++;
            L->tete->num_famille=num_famille;
            num_famille++;
            fgets(ligne,400,fichier);// pour retourner a la ligne
            // ceci sert a lire le  fichier de sortie
            char a=fgetc(fichier);
            if(a=='\n' || a==EOF)break;
            else{fseek(fichier,-1,SEEK_CUR);}
            //----------------------------------
        }
        // FIN LIGNE
        fclose(fichier);
    }
    else{printf("impossible d'ouvrir le fichier d'entree");exit(0);}
}

// FICHIER DE SORTIE
void remplir_fichier_sortie(liste_animale *L,char nom_fichier[300],int hauteur,int largeur,int csb_x,int csb_y,int hauteur_b,int largeur_b,int nourriture,int seuil_energie,liste_animale **tab_famille,int taille){
    if(L==NULL){printf("erreur liste ");exit(0);}
    FILE *fichier=NULL;
    fichier=fopen(nom_fichier,"w+");
    if(fichier!=NULL){
        fprintf(fichier,"Monde %d %d\nBeauce %d %d %d %d\nEnergie Nourriture %d\nSeuil Reproduction %d\n\n#animaux un par ligne\n", hauteur,largeur,csb_x,csb_y,hauteur_b,largeur_b,nourriture,seuil_energie);
        animale *tmp=L->tete;
        while(tmp!=NULL){
            fprintf(fichier,"(%d %d) %d %d",tmp->posi_x,tmp->posi_y,tmp->direction,tmp->energie);
            fprintf(fichier," | %d %d %d %d %d",tmp->chromosome[0],tmp->chromosome[1],tmp->chromosome[2],tmp->chromosome[3],tmp->chromosome[4]);
            fprintf(fichier," %d %d %d |\n",tmp->chromosome[5],tmp->chromosome[6],tmp->chromosome[7]);
            //fprintf(fichier," %d %d\n",tmp->num_famille,distance_ani_du_beauce(tmp,hauteur,largeur,csb_x,csb_y,hauteur_b,largeur_b));
            tmp=tmp->suivant;
        }
        fprintf(fichier,"\n\n");
        // on rempli nos familles
        int i;
        for(i=0;i<taille;i++){
            tmp=tab_famille[i]->tete;
            if(tmp==NULL){fprintf(fichier,"liste vide de la famille %d\n\n",i);}
            else{
                while(tmp!=NULL){
                    fprintf(fichier,"(%d %d) %d %d",tmp->posi_x,tmp->posi_y,tmp->direction,tmp->energie);
                    fprintf(fichier," | %d %d %d %d %d",tmp->chromosome[0],tmp->chromosome[1],tmp->chromosome[2],tmp->chromosome[3],tmp->chromosome[4]);
                    fprintf(fichier," %d %d %d |\n",tmp->chromosome[5],tmp->chromosome[6],tmp->chromosome[7]);
                    /*fprintf(fichier," %d %d |\n",tmp->num_famille,distance_ani_du_beauce(tmp,hauteur,largeur,csb_x,csb_y,hauteur_b,largeur_b));*/
                    tmp=tmp->suivant;
                }
                fprintf(fichier,"\n");
            }
        }
        fclose(fichier);
    }
    else{printf("impossbile d ecrire dans fichier de sortie");exit(0);}
}


// fonction qui recupere les donnes ecris sur le terminal
void usage(char *prog){
    fprintf(stderr, "Usage: %s r n input.txt output.txt [c]\n\n\tr: 0 (without randomness) or 1 (with randomness)\n\tn: number of iterations\n\tc: [option] nombre of generated images after the n iterations\n", prog);
}

void recup_d_lc(int argc, char** argv,int *a,int *n,int *c,char input[],char output[]){
   *n = 0;
   *c = 0;
   if(argc<5 || argc>6) {
      usage(argv[0]);
      exit(0);
   }
   if(sscanf(argv[1], "%d", a)!=1 || (*a != 1 && *a!=0)) {
      fprintf(stderr, "incorrect value for a (%s)\n", argv[1]);
      usage(argv[0]);
      exit(0) ;
   }
   if(sscanf(argv[2], "%d", n)!=1) {
      fprintf(stderr, "incorrect value for n (%s)\n", argv[2]);
      usage(argv[0]);
       exit(0) ;}
    strcpy(input,argv[3]);
    strcpy(output,argv[4]);
   
   if(argc>5 && sscanf(argv[5], "%d", c)!=1) {
      fprintf(stderr, "incorrect value for c (%s)\n", argv[3]);
      usage(argv[0]);
      exit(0) ;
   }
   if(*a) {
      srand((unsigned int) time(NULL));
   }
}

// FIN ---------------------------------------------------------------------------------------------------------------------------------------------


// FONCTIONS FAMILLES

// DEBUT ---------------------------------------------------------------------------------------------------------------------------------------------
liste_animale **init_tab_famille(int taille){
    liste_animale **T=NULL;
    T=(liste_animale**) malloc(taille*sizeof(liste_animale*));
    if(T==NULL){printf("erreur allocation tableau de famille");exit(0);}
    // on initialise les cases du tableau met aussi T[i]->nb_ani à 0
    int i;
    for(i=0;i<taille;i++){
        T[i]=init_liste_animale();
    }
    return T;
}

void remplir_tab_famille(liste_animale L,int taille,liste_animale **T){// si on met un pointeur ca va modifier la liste donc on met pas de pointeur pour ne pas la modifier
    if(L.tete==NULL){printf("liste animale vide on ne peut rien faire ");exit(0);}
    // on affecte chaque  animal à sa famille dans le tableau
    animale *a=L.tete;
    while(a!=NULL){
        ajout_deb_liste_ani(T[a->num_famille],a->posi_x,a->posi_y,a->direction,a->energie,a->chromosome);
        T[a->num_famille]->tete->num_famille=a->num_famille;
        T[a->num_famille]->nb_animale++;
        a=a->suivant;
    }
    
}
//distance_ani_du_beauce(animale *a,const int hauteur,const int largeur,int coin_x_b,int coin_y_b,const int hauteur_b,const int largeur_b)
// Attention on trie que si le beauce existe et liste non vide


// on echange les valeurs de 2 ANIMAUX
void echange_animale(animale *s,animale *d){
    if(s==NULL || d==NULL){printf("erreur copie aniamle");exit(0);}
    int px,py,e,di,nf;
    // copie
    px=d->posi_x;
    py=d->posi_y;
    di=d->direction;
    nf=d->num_famille;
    e=d->energie;
    //
    d->posi_x=s->posi_x;
    d->posi_y=s->posi_y;
    d->direction=s->direction;
    d->num_famille=s->num_famille;
    d->energie=s->energie;
    //
    s->posi_x=px;
    s->posi_y=py;
    s->direction=di;
    s->num_famille=nf;
    s->energie=e;
    int i,c;
    for(i=0;i<8;i++){
        c=s->chromosome[i];
        s->chromosome[i]=d->chromosome[i];
        d->chromosome[i]=c;
    }
}


// ON TRIE PAR RAPPORT A LA DISTANCE DU CENTRE DU BEAUCE : TRIE A BULLE
void trier_liste_animale(liste_animale *L,const int hauteur,const int largeur,int coin_x_b,int coin_y_b,const int hauteur_b,const int largeur_b){
    if(hauteur_b==0 && largeur_b==0){printf("beauce null on ne peut pas trier");exit(0);}
    if(L==NULL || L->tete==NULL){printf("attention liste vide ");exit(0);}
    animale *tmp1=L->tete,*tmp2;
    while(tmp1->suivant!=NULL){
        tmp2=tmp1->suivant;
        while(tmp2!=NULL){
            int d1=distance_ani_du_beauce(tmp1,hauteur,largeur,coin_x_b,coin_y_b,hauteur_b,largeur_b);
            int d2=distance_ani_du_beauce(tmp2,hauteur,largeur,coin_x_b,coin_y_b,hauteur_b,largeur_b);
            if(d1>=d2){
                echange_animale(tmp1,tmp2);
            }
            tmp2=tmp2->suivant;
        }
        tmp1=tmp1->suivant;
    }
}

// FIN ---------------------------------------------------------------------------------------------------------------------------------------------

// FONCTIONS IMAGES

// DEBUT ---------------------------------------------------------------------------------------------------------------------------------------------

// initialise une image du monde avec 128 128 128 comme couleurs
Image *init_image_monde(const int hauteur,const int largeur){
    Image *image=NULL;
    image=(Image *) malloc(sizeof(Image));
    if(image==NULL){printf("erreur init image monde 3");exit(0);}
    
    image->monde_pixel=NULL;
    image->monde_pixel=(Pixel **) malloc(hauteur*sizeof(Pixel*));
    if(image->monde_pixel==NULL){printf("erreur init image monde 1");exit(0);}
    int i,j;
    for(i=0;i<hauteur;i++){
        image->monde_pixel[i]=NULL;
        image->monde_pixel[i]=(Pixel *) malloc(largeur*sizeof(Pixel));
        if(image->monde_pixel[i]==NULL){printf("erreur init image monde 2");exit(0);}
        for(j=0;j<largeur;j++){
            image->monde_pixel[i][j].R=128;
            image->monde_pixel[i][j].V=128;
            image->monde_pixel[i][j].B=128;
        }
    }
    image->hauteur=hauteur;
    image->largeur=largeur;
    return image;
}


// le centre apparait aussi avec un vert plus clair
void remplir_beauce_image(Image *image,int csb_x,int csb_y,int hauteur_b,int largeur_b){// en hauteur est largeur et largeur est la hauteu
    if(hauteur_b==0 && largeur_b==0){printf("beauce null on ne peut pas remplir l image beauce");exit(0);}
    int h=hauteur_b;
    int l=largeur_b;
    reajuster_beauce(image->hauteur,image->largeur,&h,&l);
    int x,y,tmpx,tmpy;;
    for(x=0;x<h;x++){
        for(y=0;y<l;y++){
            tmpx=x+csb_x;
            tmpy=y+csb_y;
            if(tmpx>=image->hauteur) tmpx=tmpx-image->hauteur;
            if(tmpy>=image->largeur) tmpy=tmpy-image->largeur;
            if(tmpx>image->hauteur-1 || tmpx<0 || tmpy>image->largeur-1 || tmpy<0){printf("erreur dans coordonnes beauce\n");exit(0);}
            image->monde_pixel[tmpx][tmpy].R=0;
            image->monde_pixel[tmpx][tmpy].V=170;
            image->monde_pixel[tmpx][tmpy].B=0;
            
        }
    }
    int c_x,c_y;
    centre_beauce(&c_x,&c_y,image->hauteur,image->largeur,csb_x,csb_y,h,l);
    //printf("centre beauce %d %D\n",c_x,c_y);
    image->monde_pixel[c_x][c_y].R=0;
    image->monde_pixel[c_x][c_y].V=80;
    image->monde_pixel[c_x][c_y].B=0;
}


void remplir_nourriture_image(Image *image,int **monde){
    int x,y;
    for(x=0;x<image->hauteur;x++){
        for(y=0;y<image->largeur;y++){
            if(monde[x][y]!=0){
                image->monde_pixel[x][y].R=0;
                image->monde_pixel[x][y].V=0;
                image->monde_pixel[x][y].B=0;
                }
        }
    }
}
Pixel *tableau_couleur_famille(int nb_famille){
    nb_famille=nb_famille+10;
    Pixel *T=NULL;
    T=(Pixel *)malloc(nb_famille*sizeof(Pixel));
    if(T==NULL){printf("erreur d'alocation tableau couleur famille");exit(0);}
    int i;
    for(i=0;i<nb_famille;i++){
        T[i].R=rand()%256;
        T[i].V=rand()%256;
        T[i].B=rand()%256;
    }
    return T;
}


/*on parcourt notre liste d animaux, on rajoute sa couleur de famille dans le monde et on stcoke dans un tableau a 2 dimension son energie
 puis on passe a l ani  si case diferente alors suivant sinon si de meme case avec energie superieur(comparer avec energie dans tableau) alors on met dans la meme case la couleurs de la nouvelle famille puis ainsi de suite*/
    
void remplir_liste_animaux_image(Image *image,liste_animale *L,Pixel T_color_fami[]){
    if(L==NULL || L->tete==NULL){printf("attention liste vide pour remplir liste animaux image");exit(0);}
    int **tab=creer_monde(image->hauteur,image->largeur);
    animale *tmp=L->tete;
    while(tmp!=NULL){
        if(tmp->energie<=0){printf("energie ani nulle ou negative dans rempli liste animaux");exit(0);}
        if(tab[tmp->posi_x][tmp->posi_y]<tmp->energie){
            tab[tmp->posi_x][tmp->posi_y]=tmp->energie;
            image->monde_pixel[tmp->posi_x][tmp->posi_y].R=T_color_fami[tmp->num_famille].R;
            image->monde_pixel[tmp->posi_x][tmp->posi_y].V=T_color_fami[tmp->num_famille].V;
            image->monde_pixel[tmp->posi_x][tmp->posi_y].B=T_color_fami[tmp->num_famille].B;
        }
        tmp=tmp->suivant;
    }
    liberer_monde(tab,image->hauteur,image->largeur);
}

void creer_fichier_image_monde(int temps,liste_animale *L,int **monde,const int hauteur,const int largeur,int csb_x,int csb_y,const int h_b,const int l_b,Pixel T_color_fami[],Image *image){
    if(h_b!=0 && l_b!=0){remplir_beauce_image(image,csb_x,csb_y,h_b,l_b);}
    remplir_nourriture_image(image,monde);
    remplir_liste_animaux_image(image,L,T_color_fami);
    FILE *fichier=NULL;
    char tmp[100];
    sprintf(tmp, "%s%d","image", temps);
    strcat(tmp,".ppm");
    fichier=fopen(tmp,"w");
    if(fichier != NULL){
        fprintf(fichier,"P3\n%d %d\n255\n",image->largeur,image->hauteur);
        int x,y;
        for(x=0;x<image->hauteur;x++){
            for(y=0;y<image->largeur;y++){
                fprintf(fichier,"%d %d %d\n",image->monde_pixel[x][y].R,image->monde_pixel[x][y].V,image->monde_pixel[x][y].B);
            }
        }
        fclose(fichier);
    }
    else{
        printf("impossible d'ouvrir fichier : %s",tmp);
    }
    
}
//FIN--------------------------------------------------------------------------------------------------------------------------------------

// FONCTION LIBERER MEMOIRE

//DEBUT--------------------------------------------------------------------------------------------------------------------------------------

void liberer_monde(int **monde,int h,int l){
    int i;
    for(i=0;i<h;i++){
        free(monde[i]);
    }
    free(monde);
}

void free_liste_ani(liste_animale *L){
    if(L==NULL){printf("erreur d'allocation");return;}
    animale *tmp,*tmp1;
    tmp=L->tete;
    while(tmp!=NULL){
        tmp1=tmp;
        tmp=tmp->suivant;
        free(tmp1);
    }
    free(L);
    
}

void liberer_image(Image *image){
    int i;
    for(i=0;i<image->hauteur;i++){
            free(image->monde_pixel[i]);
        }
    free(image->monde_pixel);
    free(image);
    }
    

void liberer_tab_famille(liste_animale **tab_famille, int taille){
    int i;
    for(i=0;i<taille;i++){
        free_liste_ani(tab_famille[i]);
    }
    free(tab_famille);
}

//Fin--------------------------------------------------------------------------------------------------------------------------------------

