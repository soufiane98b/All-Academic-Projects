//
//  main.c
//  Architecture Projet
//
//  Created by soufiane and nabil on 18/01/2020.

#include <stdio.h>
#include "Fonctions.h"
int main(int argc, const char * argv[]) {
    char **base_instru=creer_base_instru();
    nb **T_memoire=creer_tab_memoire();
    nb **T_registre=creer_tab_registre();
    char nom_fichier[200];
    strcpy(nom_fichier,argv[1]);
    verification_total_erreur_et_remplir_memoire(base_instru,T_memoire,nom_fichier);
    generer_fichier_sortie(T_memoire,base_instru);
    //--------------------------------------------------------------------------------------------------------------------------------------------
    /*
    int i=0;
    for(int j=0;T_memoire[i]->instruction[0][0]!=' ';j=j+1){
    printf("code operatoire: %s Dest: %s Src 1: %s Src 1: %s case_memoire : %d  valeur : %d\n",T_memoire[j]->instruction[0],T_memoire[j]->instruction[1],T_memoire[j]->instruction[2],T_memoire[j]->instruction[3],j,T_memoire[j]->valeur);
    if(j%4==0)i=i+4;
    }
    */
    //--------------------------------------------------------------------------------------------------------------------------------------------
    // SIMULATION DU PROGRAMME
    // DEBUT--------------------------------------------------------------------------------------------------------
    int test=0;
    int Z=0,N=0,C=0,PC=0;
    char instruction[4],rd[4],rn[4],S[10];
    while(PC>=0 && PC<=65536){
        if(T_memoire[PC]->instruction[0][0]==' '){printf("%s\n",T_memoire[PC]->instruction[0]);exit(0);}
        strcpy(instruction,T_memoire[PC]->instruction[0]);
        strcpy(rd,T_memoire[PC]->instruction[1]);
        strcpy(rn,T_memoire[PC]->instruction[2]);
        strcpy(S,T_memoire[PC]->instruction[3]);
        test=F_instru_G(instruction,base_instru,rd,rn,S,T_registre,&Z,&N,&C,T_memoire,&PC);
        if(test==2)break;
        if(test==0)PC=PC+4;
        //printf("PC=%d test=%d \n",PC,test);
    }
    //FIN SIMULATION------------------------------------------------------------------------------------------------------------
    liberer_base_instru(base_instru);
    liberer_tab_registre(T_registre);
    liberer_tab_memoire(T_memoire);
    return 0;
}

