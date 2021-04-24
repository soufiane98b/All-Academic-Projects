//
//  FONCTIONS.h
//  Projet C L2 Selection Naturelle
//
//  Crée par Soufiane Boustique  le 11/12/2019.

#ifndef FONCTIONS_h
#define FONCTIONS_h

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <math.h>
#define max(a,b) (a>=b?a:b)
#define min(a,b) (a<=b?a:b)

// STRUCTURES

typedef struct animale animale;
struct animale{
    int posi_x;// entre 0 et h-1
    int posi_y;// entre 0 et l-1
    int direction;// entre 0 et 7
    int energie;
    int chromosome[8]; // RQ NOS GENES SONT TOUS >=1
    int num_famille;
    animale *suivant;

};

typedef struct liste_animale liste_animale;
struct liste_animale{
    int nb_animale;
    animale *tete;
};

// FONCTIONS GENERALES

void cellule_voisine(int *posi_x,int *posi_y,int *direction,const int hauteur,const int largeur);
void deplacer(int *posi_x,int *posi_y,int *direction,int *gene,const int hauteur,const int largeur);
void cellule_aleatoire(int *posi_x,int *posi_y,const int hauteur,const int largeur);
int gene_aleatoire(int chromosome[]);
void ajouter_nourriture_alea_monde(int **monde,int nourriture,const int hauteur,const int largeur);
void ajouter_nourriture_alea_beauce(int **monde,int nourriture,const int hauteur,const int largeur,int coin_x_b,int coin_y_b,const int hauteur_b,const int largeur_b);
void centre_beauce(int *c_x,int *c_y,const int hauteur,const int largeur,int coin_x_b,int coin_y_b,const int hauteur_b,const int largeur_b);
int distance_ani_du_beauce(animale *a,const int hauteur,const int largeur,int coin_x_b,int coin_y_b,const int hauteur_b,const int largeur_b);
int **creer_monde(const int hauteur,const int largeur);
void afficher_monde(int **monde,const int hauteur,const int largeur);
void reajuster_beauce(const int hauteur,const int largeur,int *hauteur_b,int *largeur_b);
int *initTabF(int nb_famille);
void liberer_monde(int **monde,int h,int l);

// FONCTIONS ANIMALES

liste_animale *init_liste_animale(void);
animale *creer_animale(int posi_x,int posi_y,int direction,int energie,int chromosome[8]);
void ajout_deb_liste_ani(liste_animale *L,int posi_x,int posi_y,int direction,int energie,int chromosome[8]);
void affichier_liste_ani(liste_animale *L);
void supprimer_liste_ani(liste_animale *L,animale *a);
void nourrir_et_baisser_energie_animale(animale *a,int **monde,int nourriture);
int dupliquer_ou_tuer_animale_liste(liste_animale *L, animale *a,int seuil_energie);
void deplace_animale(animale *a,const int hauteur,const int largeur);
void free_liste_ani(liste_animale *L);

// FONCTIONS SUR FICHIERS
int que_espace_retour_ligne_dieze(char *fin);
void verifier_syntaxe_fichier(char nom_fichier[300]);
void recupe_donnees_fichier(char nom_fichier[300],int *hauteur,int *largeur,int *csb_x,int *csb_y,int *hauteur_b,int *largeur_b,int *nourriture,int *seuil_energie,liste_animale *L);
void remplir_fichier_sortie(liste_animale *L,char nom_fichier[100],int hauteur,int largeur,int csb_x,int csb_y,int hauteur_b,int largeur_b,int nourriture,int seuil_energie,liste_animale **tab_famille,int taille);
void usage(char *prog);
void recup_d_lc(int argc, char** argv,int *a,int *n,int *c,char input[],char output[]);

// FONCTIONS FAMILLES

void afficherTab(int tab[],int taille);
liste_animale **init_tab_famille(int taille);
void remplir_tab_famille(liste_animale L,int taille,liste_animale **T);
void echange_animale(animale *s,animale *d);
void trier_liste_animale(liste_animale *L,const int hauteur,const int largeur,int coin_x_b,int coin_y_b,const int hauteur_b,const int largeur_b);

// FONCTIONS SUR IMAGES

typedef struct Pixel
{
    int R,V,B;
} Pixel;

typedef struct Image
{
    int largeur,hauteur;// c la hauteur et largeur du monde
    Pixel** monde_pixel;// chaque pixel represente une case du monde 
} Image;

Image *init_image_monde(const int hauteur,const int largeur);
void afficher_image(Image *image);
void remplir_beauce_image(Image *image,int csb_x,int csb_y,const int hauteur_b,const int largeur_b);
void remplir_nourriture_image(Image *image,int **monde);
Pixel *tableau_couleur_famille(int nb_famille);
void remplir_liste_animaux_image(Image *image,liste_animale *L,Pixel T_color_fami[]);
void creer_fichier_image_monde(int temps,liste_animale *L,int **monde,const int hauteur,const int largeur,int csb_x,int csb_y,const int h_b,const int l_b,Pixel T_color_fami[],Image *image);


// FONCTION LIBERER MEMOIRE
void liberer_image(Image *image);
void liberer_tab_famille(liste_animale **tab_famille, int taille);

#endif /* FONCTIONS_h */
