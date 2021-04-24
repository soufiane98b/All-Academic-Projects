//
//  main.c
//  Projet C L2 Selection Naturelle
//
//  Crée par Soufiane Boustique  le 11/12/2019.
#include "FONCTIONS.h"
#include <stdio.h>
#include <string.h>
#include <ctype.h>

int main(int argc,char ** argv) {
    // PARAMETRES DE DEPART
    int n;
    int c;
    int a;
    char input[300];
    char output[300];
    recup_d_lc(argc,argv,&a,&n,&c,input,output);
    if(a==1) {
        srand((unsigned int) time(NULL));
    }
    liste_animale *L=init_liste_animale();
    int temps=n,i,chaque=100000,step=1;
    int hauteur,largeur,csb_x,csb_y,hauteur_b,largeur_b,nourriture,seuil_energie;
    verifier_syntaxe_fichier(input);
    recupe_donnees_fichier(input,&hauteur,&largeur,&csb_x,&csb_y,&hauteur_b,&largeur_b,&nourriture,&seuil_energie,L);
    // affecte aussi à chaque ligne un numero de famille
    int N=L->nb_animale;
    Pixel *T_color_fami = NULL;
    T_color_fami=tableau_couleur_famille(N);// on initialise a chaque famille une couleur differente
    if(L==NULL || L->tete==NULL){printf("erreur: liste d'animaux vide \n");return 0;}

    //------------------------------------------------------------------------------------------------------------------------
    // ON CHARGE LE MONDE
    int **monde=creer_monde(hauteur,largeur);// avec 0 nourriture sur toutes les cases
    reajuster_beauce(hauteur,largeur,&hauteur_b,&largeur_b);// on reajuste la beauce si necessaire
    animale *tmp=L->tete,*tmp2;
    int condi;
    // ON EFFECTUE TEMPS FOIS L EVOLUTION --------------------------------------------------------------------------------------------
    int image=1;
    for(i=1;i<=temps+c;i++){
        if(i>temps){
            // ATTENTION : On cree nos images si c>0
            Image *im=init_image_monde(hauteur,largeur);
            creer_fichier_image_monde(image,L,monde,hauteur,largeur,csb_x,csb_y,hauteur_b,largeur_b,T_color_fami,im);
            liberer_image(im);
            image++;
            if(i==temps+c)break;// on arrette sinon on efectue une iteration qui correpsond pas a l image finale
        }
        ajouter_nourriture_alea_monde(monde,nourriture,hauteur,largeur);
        if(hauteur_b!=0 && largeur_b!=0)
            {ajouter_nourriture_alea_beauce(monde,nourriture,hauteur,largeur,csb_x,csb_y,hauteur_b,largeur_b);}
        // ON PARCOURT LA LISTE DES ANIMAUX POUR EFFECTUER LE PROCESSUS
        tmp=L->tete;
        while(tmp!=NULL){
            deplace_animale(tmp,hauteur,largeur);
            nourrir_et_baisser_energie_animale(tmp,monde,nourriture);//ici msj le monde:le premier animale qui mange la nourrtirure fait disparaitre celle ci du monde
            condi=dupliquer_ou_tuer_animale_liste(L,tmp,seuil_energie);//si animal cree il est place en tete de liste pour ne pas le traiter ce tour ci sinon il est tue et renvoi 1 si tue sinon 0
            tmp2=tmp;
            tmp=tmp->suivant;
            if(condi==1){free(tmp2);tmp2=NULL;} // on free l animale supprime au fur et à mesure de l'execution
        }
        // on arrette le programme si la liste ne contient plus d'animaux ca veut dire tous le animaux sont morts
        if(L->tete==NULL){printf("extinction d'animaux plus d'animaux dans la liste \n\n");return 0;}
        if((i%chaque)==0){printf("\n------------------------------------------------%d*%d Etapes\n",step,chaque);step++;}
    }
    // FIN TEMPS  --------------------------------------------------------------------------------------------
    trier_liste_animale(L,hauteur,largeur,csb_x,csb_y,hauteur_b,largeur_b);
    liste_animale **tab_famille=init_tab_famille(N);
    remplir_tab_famille(*L,N,tab_famille);
    remplir_fichier_sortie(L,output,hauteur,largeur,csb_x,csb_y,hauteur_b,largeur_b,nourriture,seuil_energie,tab_famille,N);
    // ON LIBERE LA MEMOIRE
    free_liste_ani(L);
    L=NULL;
    free(T_color_fami);
    T_color_fami=NULL;
    liberer_monde(monde,hauteur,largeur);
    liberer_tab_famille(tab_famille,N);
 
}
        



