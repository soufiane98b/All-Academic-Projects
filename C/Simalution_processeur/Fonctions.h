//
//  Fonctions.h
//  Architecture Projet
//
//  Created by soufiane and nabil on 18/01/2020.
//

#ifndef Fonctions_h
#define Fonctions_h

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>
#include <errno.h>
#include<ctype.h>


// STRUCTURE
typedef struct nb nb;
struct nb{
    //char test;// 0 veut dire vide 1 veut dire rempli // 2 veut dire case d instruction
    int valeur; // Valeur adresse sur 1 octet
    char **instruction;
    /* ou programme[i][j]:[4][20]
      et i==0: code operatoire
         i==1: Dest
         i==2: Src 1
         i==3: Src 2
     j: nombre de caractere
     
     */
};


// FONCTIONS DIVERSES POUR INSTRUCTION
char *hexadecimal(int entier);
nb **creer_tab_memoire(void);
nb **creer_tab_registre(void);
int donner_place_memoire(nb **memoire);
char **creer_base_instru(void);
int convertir_instru(char instruction[4],char **base_instru);
int registre_ou_nombre(char S[32769]);
int convertir_nombre(char S[17],int ligne,int test);
int convertir_numero_registre(char rn[4],int ligne);
char transformer_8(int n);
unsigned char transformer_8_non_signe(int n);
short transformer_16(int n);
void test_bon_intervalle_adresse(int n);
void test_bon_intervalle_16(int n);
void test_bon_intervalle_8(int n);
void modifier_Z(int resultat,int *Z);
void modifier_C_D(int resultat,int *C);
void modifier_C_G(int resultat,int *C);
void modifier_N(int resultat,int *N);
int n_alea(int a,int b);
int *convertir_en_2_octet(int A);
int convertir_de_2_octet_en_1(int faible,int fort);


//FONCTIONS D'INSTRUCTIONS
void F_instru_arith_logi(char instruction[4],char **base_instru,char rd[4] ,char rn[4],char S[10],nb **T_registres,int *Z,int *N,int *C);
void F_instru_transfert(char instruction[4],char **base_instru,char rd[4],char rn[4],char S[10],nb **T_registres,int *Z,int *N,int *C,nb **T_memoire);
int F_instru_saut(char instruction[4],char **base_instru,nb **T_registres,char S[10],int *PC,int *Z,int *N,int *C);
void F_instru_es(char instruction[4],char **base_instru,char rd[4],nb **T_registres,int *Z,int *N,int *C);
void F_instru_diverses(char instruction[4],char **base_instru,char rd[4] ,char rn[4],char S[10],nb **T_registres,int *Z,int *N,int *C);
int F_instru_G(char instruction[4],char **base_instru,char rd[4],char rn[4],char S[10],nb **T_registres,int *Z,int *N,int *C,nb **T_memoire,int *PC);

// FONCTIONS DIVERSES POUR VERIFICATION
char *tronquer(char *s,int i);
int apres(char *s,char **base_instru,int ligne);
int verifier_si_etiquette(char *s,char **base_intru,char ligne,int *u);
int verif_si_instru(char *s,char **base_instru,int ligne);
char **creer_tab_etiq(void);
int ishex(char c);


// FONCTIONS DE VERIFICATION
void verif_parametre(int ligne,int in,char *s);
void remplir_T_memoire_verif_nombre(int ligne,int in,char *s,nb **T_memoire,char **base_instru);
void remplir_verif_etiq(nb **T_memoire,char **T_etiq,char **base_instru,int ligne);
void verification_total_erreur_et_remplir_memoire(char **base_instru,nb **T_memoire,char nom_fichier[30]);

// FONCTIONS DE TRADUCTION
char *rajouter_0(char *ch);
int D(char h);
void s_convertir(int T[],char *final);
void generer_fichier_sortie(nb **T_memoire,char **base_instru);

// FONCTION LIBERER ESPACE MEMOIRE
void liberer_base_instru(char **base_instru);
void liberer_tab_registre(nb **tab_registre);
void liberer_tab_memoire(nb **tab_memoire);
void liberer_tab_etiq(char **tab_etiq);
#endif /* Fonctions_h */
