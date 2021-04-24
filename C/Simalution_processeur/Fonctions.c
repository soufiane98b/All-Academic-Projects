//
//  Fonctions.c
//  Architecture Projet
//
//  Created by soufiane boustique and nabil mouzali  on 18/01/2020.

#include "Fonctions.h"


// FONCTIONS DIVERSES

//DEBUT --------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------

nb **creer_tab_memoire(void){
    nb **memoire=(nb **) malloc(65536*sizeof(nb *));
    int i;
    for(i=0;i<65536;i++){// chaque valeur est de 8 bits signe
        memoire[i]=NULL;
        memoire[i]=malloc(sizeof(nb));
        if(memoire[i]==NULL){printf("impossbile de creer tab registre \n");exit(0);}
        memoire[i]->valeur=0;
        memoire[i]->instruction=malloc(4*sizeof(char *));
        memoire[i]->instruction[0]=malloc(4);
        strcpy(memoire[i]->instruction[0]," ");
        memoire[i]->instruction[1]=malloc(4);
        strcpy(memoire[i]->instruction[1]," ");
        memoire[i]->instruction[2]=malloc(4);
        strcpy(memoire[i]->instruction[2]," ");
        memoire[i]->instruction[3]=malloc(9);
        strcpy(memoire[i]->instruction[3]," ");
    }
    return memoire;
}

nb **creer_tab_registre(void){// chaque case contient une adresse  ou un nombre memoire ATTENTION R0==0 TG
    nb **T_registres=NULL;
    T_registres=(nb **)malloc(32*sizeof(nb *));
    if(T_registres==NULL){printf("impossible de creer tab de registres \n");exit(0);}
    int i;
    for(i=0;i<32;i++){
        T_registres[i]=NULL;
        T_registres[i]=malloc(sizeof(nb *));
        if(T_registres[i]==NULL){printf("impossbile de creer tab registre \n");exit(0);}
        T_registres[i]->valeur=0;
    }
    return T_registres;
}


char **creer_base_instru(void){
     char T[32][4]={"add","sub","mul","div","or","xor","and","shl","ldb","ldw","stb","stw","---","---","---","---","jmp","jzs","jzc","jcs","jcc","jns","jnc","---","---","---","---","---","in","out","rnd","hlt"};
    char **p=malloc(32*sizeof(char *));
    for(int i=0;i<32;i++){
        p[i]=malloc(4);
        strcpy(p[i],T[i]);
    }
    return p;
}


int convertir_instru(char instruction[100],char **base_instru){
    int i;
    for(i=0;i<32;i++){
        if(strcmp(instruction,base_instru[i])==0){return i;}
    }
    return -1;// ie instru n'existe pas
}

int registre_ou_nombre(char S[10]){
    if(S[0]=='#') return 0;
    return 1;
}


char transformer_8(int n){// 1 octet signe
    char b=n;
    return b;
}
short transformer_16(int n){// 2 octet signe
    short b=n;
    return b;
}

unsigned char transformer_8_non_signe(int n){// 1 octet non signe
    return n;
}

void test_bon_intervalle_adresse(int n){
    if(n > pow(2,16)-1 || n < 0){printf(" votre accedez à une adresse memoire qui n'existe pas \n ");exit(0);}
}

void test_bon_intervalle_16(int n){
    if(n > pow(2,15)-1 || n < -pow(2,15)){printf(" nombre rentre non code sur 16 bit signe \n ");exit(0);}
}
void test_bon_intervalle_8(int n){
    if(n > pow(2,7)-1 || n < -pow(2,7)){printf(" nombre rentre non code sur 16 bit signe \n ");exit(0);}
}

void modifier_Z(int resultat,int *Z){
    if(*Z!=0 && *Z!=1){printf("erreur valeur bit d etat \n");exit(0);}
    if(resultat==0) *Z=1;
    else{*Z=0;}
}

void modifier_C_D(int resultat,int *C){
    if(*C!=0 && *C!=1){printf("erreur valeur bit d etat \n");exit(0);}
    if(resultat%2==0) *C=0;// retenue
    else{*C=1;}
}

void modifier_C_G(int resultat,int *C){
    if(*C!=0 && *C!=1){printf("erreur valeur bit d etat \n");exit(0);}
    if(resultat <0) *C=1;// retenue
    else{*C=0;}
}
void modifier_C(int resultat, int *C){
    if(resultat>32767 || resultat <-32768){
        *C=1;
    }
    else{*C=0;}
}


void modifier_N(int resultat,int *N){
    if(*N!=0 && *N!=1){printf("erreur valeur bit d etat \n");exit(0);}
    if(resultat<0) *N=1;// bit du poid fort
    else{N=0;}
}

int n_alea(int a,int b){
    srand((unsigned int) time(NULL));
    int n;
    if(a<=b){
        n=rand()%(b-a);
        n=n+a;
    }
    else{
        n=rand()%(a-b);
        n=n+b;
    }
    return n;
}

int *convertir_en_2_octet(int A){
    int faible=A & 255;
    int fort=(A & (255 << 8 )) >> 8;
    faible=transformer_8(faible);
    fort=transformer_8(fort);
    int T[2]={faible,fort};
    int *p=T;
    return p;
}

int convertir_de_2_octet_en_1(int faible,int fort){
    faible=transformer_8_non_signe(faible);
    fort=transformer_8_non_signe(fort);
    fort=fort<<8;
    short final=faible+fort;
    return final;
}



// FIN----------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------


//FONCTIONS D'INSTRUCTIONS

// DEBUT--------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------


// on suppose que les nombres dans les registres  sont bien sur 16 bits en C2 au depart
void F_instru_arith_logi(char instruction[4],char **base_instru,char rd[4] ,char rn[4],char S[10],nb **T_registres,int *Z,int *N,int *C){ // car le nombre est sur 16 bit mais C2 donc 15 bits +1 pour caractere #
    T_registres[0]->valeur=0;
    int in=convertir_instru(instruction,base_instru);
    if(in<0 || in>7){printf("erreur F_instru_arith_logi");exit(0);}
    int d=convertir_numero_registre(rd,0),n=convertir_numero_registre(rn,0),s;
    test_bon_intervalle_16(T_registres[n]->valeur);
    if(registre_ou_nombre(S)==1){s=convertir_numero_registre(S,0);s=T_registres[s]->valeur;}
    else{s=convertir_nombre(S,0,0);}
    test_bon_intervalle_16(s);
        switch(in){
            case 0: T_registres[d]->valeur=T_registres[n]->valeur + s;break;
            case 1: T_registres[d]->valeur=T_registres[n]->valeur - s;break;
            case 2: T_registres[d]->valeur=transformer_8(T_registres[n]->valeur) * transformer_8(s);break;
            case 3: T_registres[d]->valeur=T_registres[n]->valeur / s;break;
            case 4: T_registres[d]->valeur=T_registres[n]->valeur | s;break;
            case 5: T_registres[d]->valeur=T_registres[n]->valeur ^ s;break;
            case 6: T_registres[d]->valeur=T_registres[n]->valeur & s;break;
            }
    if(in>=0 && in<=6){
        modifier_C(T_registres[d]->valeur,C);
    }
    if(s>0 && in==7){
        short tmp=T_registres[n]->valeur << (s-1);
        modifier_C_G(tmp,C);
        T_registres[d]->valeur=T_registres[n]->valeur << s;
    }
    if(s<0 && in==7){
        s=-s;
        short tmp=T_registres[n]->valeur >> (s-1);
        modifier_C_D(tmp,C);
        T_registres[d]->valeur=T_registres[n]->valeur >> s;
        s=-s;
    }
    
    T_registres[d]->valeur=transformer_16(T_registres[d]->valeur);
    modifier_Z(T_registres[d]->valeur,Z);
    modifier_N(T_registres[d]->valeur,N);
    T_registres[0]->valeur=0;// car le registre 0 ne peut etre modifie
}


// on suppose que les contenus des registres  sont bien sur 16 bits en C2 au depart
void F_instru_transfert(char instruction[4],char **base_instru,char rd[4],char rn[4],char S[10],nb **T_registres,int *Z,int *N,int *C,nb **T_memoire){
    T_registres[0]->valeur=0;
    int in=convertir_instru(instruction,base_instru);
    if(in<8 || in>11){printf("erreur F_instru_transfert");exit(0);}
    int d=convertir_numero_registre(rd,0),n=convertir_numero_registre(rn,0),s;
    test_bon_intervalle_16(T_registres[n]->valeur);
    if(registre_ou_nombre(S)==1){s=convertir_numero_registre(S,0);s=T_registres[s]->valeur;}
    else{s=convertir_nombre(S,0,0);}
    test_bon_intervalle_16(s);
    int a=0;
    if(in==8){
        //rd<-contenu de l’adresse (rn+S), sur 1 octet
        test_bon_intervalle_adresse(T_registres[n]->valeur+s);
        test_bon_intervalle_8(T_memoire[T_registres[n]->valeur+s]->valeur);
        T_registres[d]->valeur=T_memoire[T_registres[n]->valeur+s]->valeur;
        a=transformer_8(T_memoire[T_registres[n]->valeur+s]->valeur);
    }
    if(in==9){
        //rd<-contenu de l’adresse (rn+S), sur 2 octets
        test_bon_intervalle_adresse(T_registres[n]->valeur+s);
        test_bon_intervalle_adresse(T_registres[n]->valeur+s+1);
        int final;
        test_bon_intervalle_8(T_memoire[T_registres[n]->valeur+s]->valeur);
        test_bon_intervalle_8(T_memoire[T_registres[n]->valeur+s+1]->valeur);
        T_memoire[T_registres[n]->valeur+s+1]->valeur=transformer_8_non_signe(T_memoire[T_registres[n]->valeur+s+1]->valeur);
        T_memoire[T_registres[n]->valeur+s]->valeur=transformer_8_non_signe(T_memoire[T_registres[n]->valeur+s]->valeur);
        final=convertir_de_2_octet_en_1(T_memoire[T_registres[n]->valeur+s]->valeur,T_memoire[T_registres[n]->valeur+s+1]->valeur);//(faible,fort) adresse (rn+S): faible (rn+S+1): fort
        test_bon_intervalle_16(final);// ATTENTION
        T_registres[d]->valeur=final;
        a=final;
    }
    if(in==10){
        //l’adresse (rd+S) reçoit le contenu de rn, sur 1 octet
        test_bon_intervalle_adresse(T_registres[d]->valeur+s);
        T_memoire[T_registres[d]->valeur+s]->valeur=transformer_8(T_registres[n]->valeur);
        a=transformer_8(T_registres[n]->valeur);
    }
    if(in==11){
        //l’adresse (rd+S) reçoit le contenu de rn, sur 2 octets
        int *p;
        int faible,fort;
        test_bon_intervalle_adresse(T_registres[d]->valeur+s);
        p=convertir_en_2_octet(T_registres[n]->valeur);
        faible=*p;fort=*(p+1);
        T_memoire[T_registres[d]->valeur+s]->valeur=faible;
        T_memoire[T_registres[d]->valeur+s+1]->valeur=fort;
        a=T_registres[n]->valeur;
    }
    *C=0;
    modifier_Z(a,Z);
    modifier_N(a,N);
    T_registres[0]->valeur=0;
}

int F_instru_saut(char instruction[4],char **base_instru,nb **T_registres,char S[10],int *PC,int *Z,int *N,int *C){
    T_registres[0]->valeur=0;
    int test=0;
    int in=convertir_instru(instruction,base_instru),s;
    if(in<16 || in>22){printf("erreur F_instru_saut");exit(0);}
    if(registre_ou_nombre(S)==1){s=convertir_numero_registre(S,0);s=T_registres[s]->valeur;}
    else{s=convertir_nombre(S,0,0);}
    s=convertir_nombre(S,0,0);
    test_bon_intervalle_16(s);
    test_bon_intervalle_adresse(s);
    switch(in){
        case 16: *PC=s;test=1;break;
        case 17: if(*Z==1){*PC=s;test=1;}break;
        case 18: if(*Z==0){*PC=s;test=1;}break;
        case 19: if(*C==1){*PC=s;test=1;}break;
        case 20: if(*C==0){*PC=s;test=1;}break;
        case 21: if(*N==1){*PC=s;test=1;}break;
        case 22: if(*N==0){*PC=s;test=1;}break;
        }
    T_registres[0]->valeur=0;
    return test;
}

void F_instru_es(char instruction[4],char **base_instru,char rd[4],nb **T_registres,int *Z,int *N,int *C){
    T_registres[0]->valeur=0;
    int in=convertir_instru(instruction,base_instru);
    if(in<28 || in>29){printf("erreur F_instru_saut");exit(0);}
    int d=convertir_numero_registre(rd,0);
    if(in==28){
        int V;
        printf("quelle valeur signe sur 16 bits voulez vous mettre dans le registre%d : ",d);
        scanf("%d",&V);
        test_bon_intervalle_16(V);
        modifier_Z(V,Z);
        modifier_N(V,N);
        T_registres[d]->valeur=V;
    }
    if(in==29){
        modifier_C(T_registres[d]->valeur,C);
        T_registres[d]->valeur=transformer_16(T_registres[d]->valeur);
        modifier_Z(T_registres[d]->valeur,Z);
        modifier_N(T_registres[d]->valeur,N);
        printf("contenu du registre %d est la valeur signe sur 16 bits : %d  \n",d,T_registres[d]->valeur);
    }
    T_registres[0]->valeur=0;
    
}

void F_instru_diverses(char instruction[4],char **base_instru,char rd[4] ,char rn[4],char S[10],nb **T_registres,int *Z,int *N,int *C){
    T_registres[0]->valeur=0;
    int in=convertir_instru(instruction,base_instru);
    if(in != 30){printf("erreur F_instru_diverses\n");exit(0);}
    int d=convertir_numero_registre(rd,0),n=convertir_numero_registre(rn,0),s,v;
    test_bon_intervalle_16(T_registres[n]->valeur);
    if(registre_ou_nombre(S)==1){s=convertir_numero_registre(S,0);s=T_registres[s]->valeur;}
    else{s=convertir_nombre(S,0,0);}
    test_bon_intervalle_16(s);
    if(in==30){
        v=n_alea(T_registres[n]->valeur,s);
        T_registres[d]->valeur=v;
        test_bon_intervalle_16(v);
        modifier_Z(v,Z);
        modifier_N(v,N);
    }
    T_registres[0]->valeur=0;
}

int F_instru_G(char instruction[4],char **base_instru,char rd[4],char rn[4],char S[10],nb **T_registres,int *Z,int *N,int *C,nb **T_memoire,int *PC){
    int in=convertir_instru(instruction,base_instru);
    if(in==31){printf("on arrette le programme \n");return 2;}
    if(in==-1){printf("erreur F_instru_G\n");exit(0);}
    int test=0;
    if(in>=0 && in<=7){
        F_instru_arith_logi(instruction,base_instru,rd,rn,S,T_registres,Z,N,C);
    }
    if(in>=8 && in<=11){
        F_instru_transfert(instruction,base_instru,rd,rn,S,T_registres,Z,N,C,T_memoire);
    }
    if(in>=16 && in<=22){
        test=F_instru_saut(instruction,base_instru,T_registres,S,PC,Z,N,C);
    }
    if(in>=28 && in<=29){
        F_instru_es(instruction,base_instru,rd,T_registres,Z,N,C);
    }
    if(in==30){
        F_instru_diverses(instruction,base_instru,rd,rn,S,T_registres,Z,N,C);
    }
    return test;
}

// FIN-----------------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------------------


// FONCTION  DIVERSES DE VERIFICATION

// DEBUT---------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------

char *tronquer(char *s,int i){
    if(i>=strlen(s) || i<0){return s;}
    char s4[10];
    int j,d=0;
    for(j=i+1;s[j]!='\0';j++){
        s4[d]=s[j];
        d++;
    }
    s4[d]='\0';
    printf("%s",s4);
    char *p=s4;
    return p;
}

// verfie si c est une etique et si oui si il est bien ecrite si ce n est pas une etique renvoi 0 donc c'est forcement une instru sinon apres faut verifier validite de l intsrtu
int verifier_si_etiquette(char *s,char **base_intru,char ligne,int *u){
    int instru=convertir_instru(s,base_intru);
    int i;
    if(instru==-1 || instru==12){// ie veut dire que c est pas une instru donc verifier que c est bien  une etiquette
        for(i=0;s[i]!='\0';i++){
            if(s[i]==':'){//c est bien une etiquette
                if(s[0]==':'){printf("erreur ligne %d, un espace n est pas une etiquette\n",ligne);exit(0);}
                if(s[i+1]=='\0') return 0; // veut dire pas colle avec prochaine
                else{*u=i;return 1;}// veut dire  colle avec prochain
            }
        }
        printf("\nerreur ligne : %d ( manque d espace apres etiquette ) ou ( espace apres instru ) \n",ligne);
            exit(0);
    }
    
    
    return 2; // ie veut dire pas colle
}


int apres(char *s,char **base_instru,int ligne){
    char instru;
    int i=0;
    while(s[i]!=':'){
        i++;
    }
    char s4[100];
    int j,d=0;
    for(j=i+1;s[j]!='\0';j++){
        s4[d]=s[j];
        d++;
    }
    s4[d]='\0';
    instru=convertir_instru(s4,base_instru);
    if(instru==-1 || instru==12 || instru==13 || instru==14 || instru==15 || instru==23 || instru==24 || instru==25 || instru==26 || instru==27){
        printf("erreur ligne: %d: ceci n'est pas une instruction ou manque espace\n",ligne);
        exit(0);
    }
    return instru;
}

int verif_si_instru(char *s,char **base_instru,int ligne){
    int instru=convertir_instru(s,base_instru);
    if(instru==-1 || instru==12 || instru==13 || instru==14 || instru==15 || instru==23 || instru==24 || instru==25 || instru==26 || instru==27){
        printf("erreur ligne: %d: ceci n'est pas une instruction\n",ligne);
        exit(0);
    }
    return instru;
}


int convertir_numero_registre(char rn[4],int ligne){// renvoi le numero de registre d'une chaine de caracete "r31" affiche erreur eventuel d ecriture
    int i,numero=0;
    for(i=0;rn[i]!='\0';i++){
        if(i==0 && rn[i]!='r'){printf("erreur ligne :%d d ecriture de registre \n",ligne+1);exit(0);}
        if(i==3){printf("erreur ligne:%d numero registre \n",ligne+1);exit(0);}
        if(i==1){
             if(isdigit(rn[i])!=0)numero=rn[i]-'0';
             else{printf("erreur ligne :%d numero registre \n",ligne+1);exit(0);}
        }
        if(i==2){
             if(isdigit(rn[i])!=0)numero=numero*10+rn[i]-'0';
             else{printf("erreur ligne :%d d ecriture de registre \n",ligne+1);exit(0);}
        }
    }
    if(sscanf(rn,"r%d",&numero)!=1){printf("erreur ligne :%d d ecriture de registre \n",ligne+1);exit(0);}
    if(numero<0 || numero>31){printf("erreur ligne %d numero registre \n",ligne+1);exit(0);}
    return numero;
}

int ishex(char c){
    if(c=='a'||c=='b'||c=='c'||c=='d'||c=='e'||c=='f')return 10+c-'a';
    if(c=='A'||c=='B'||c=='C'||c=='D'||c=='E'||c=='F')return 10+c-'A';
    return 0;
}

int convertir_nombre(char S[17],int ligne,int test){// 1: adresse 0: nb sur 16 bit
    int nb=0,i,j=1;
    if(S[1]!='h'){// #24234
        if(S[1]=='-')j=2;
        for(i=j;S[i]!='\0';i++){
            if(isdigit(S[i])==0){printf("erreur ligne %d d ecriture de S  \n",ligne+1);exit(0);}
        }
        if(sscanf(S,"#%d",&nb)!=1){printf("erreur ligne %d d ecriture de S  \n",ligne+1);exit(0);}
    }
    else{
        j=2;
        if(S[2]=='-')j=3;
        for(i=j;S[i]!='\0';i++){// #h-2a434
            if(isdigit(S[i])==0 && ishex(S[i])==0){printf("erreur ligne %d d ecriture de S  \n",ligne+1);exit(0);}
        }
        char h[17];
        if(sscanf(S,"#h%s",h)!=1){printf("erreur ligne %d d ecriture de S  \n",ligne+1);exit(0);}
        if(strtol(h,NULL,16)>70000||strtol(h,NULL,16)<-70000){printf("erreur ligne %d d ecriture de S  \n",ligne+1);exit(0);}
        nb=strtol(h,NULL,16);
        
    }
    
    if(test==1 && (nb>65535 || nb<0)){printf("erreur ligne %d n 'est pas sur 16 bits non signe car adresse \n",ligne+1);exit(0);}
    if(test==0 && (nb>32767 || nb<-32768)){printf("erreur ligne %d n 'est pas sur 16 bits signe \n",ligne+1);exit(0);}
    return nb;
}

char **creer_tab_etiq(void){
    char **T=(char **)malloc(1000*sizeof(char*));
    int i;
    for(i=0;i<1000;i++){
        T[i]=malloc(100);
        strcpy(T[i]," ");
    }
    return T;
}


// FIN-----------------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------------------

// FONCTIONS DE VERIFICATION


// DEBUT---------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------


// ON VERIFIE PARAMETRE DE LA LIGNE CONTENUE DANS s sans les nombres
void verif_parametre(int ligne,int in,char *s){
    if((in>=0 && in<=7) || in==30 ){
        if( strlen(s)<8 || strlen(s)>15 ){printf("erreur  ligne %d argument \n",ligne);exit(0);}
        if(s[0]!='r'){printf("erreur  ligne %d argument \n",ligne);exit(0);}// premier r
        int i,cmpt=0,posi1=-1,posi2=-1;
        for(i=0;s[i]!='\0';i++){// on chercher posi des 2 virgules si mal positione erreur
            if(s[i]==','){
                cmpt++;
                if(cmpt==1){
                    if(i!=2 && i!=3){printf("erreur  ligne %d argument \n",ligne);exit(0);}
                    else{posi1=i;}}
                if(cmpt==2){
                    if(i!=posi1+3 && i!=posi1+4){printf("erreur  ligne %d argument \n",ligne);exit(0);}
                    else{posi2=i;}}
                if(cmpt>2){printf("erreur  ligne %d argument \n",ligne);exit(0);}
            }
        }
        if(cmpt!=2){printf("erreur  ligne %d argument \n",ligne);exit(0);}
        if(s[posi1+1]!='r'){printf("erreur  ligne %d argument \n",ligne);exit(0);} // 2 eme r
        if(s[posi2+1]!='#'&& s[posi2+1]!='r'){printf("erreur  ligne %d argument \n",ligne);exit(0);}
        if(s[posi2+2]=='\0'){printf("erreur ligne %d argument\n",ligne);exit(0);}
    }
        
    if(in>=8 && in<=9){
        if( strlen(s)<9 || strlen(s)>17 ){printf("eerreur  ligne %d argument \n",ligne);exit(0);}
        if(s[0]!='r'){printf("erreur  ligne %d argument \n",ligne);exit(0);}// premier r
        int i,cmpt=0,posi1=-1,vi=-1;
        for(i=0;s[i]!='\0';i++){// on chercher posi de 1 virgules si mal positione erreur
            if(s[i]==','){
                cmpt++;
                if(cmpt==1){
                    if(i!=2 && i!=3){printf("erreur  ligne %d argument \n",ligne);exit(0);}
                    else{posi1=i;}}
                if(cmpt>1){printf("erreur  ligne %d argument \n",ligne);exit(0);}
            }
        }
        if(cmpt!=1){printf("erreur  ligne %d argument \n",ligne);exit(0);}
        if(s[posi1+1]!='(' || s[posi1+2]!='r'){printf("erreur ligne %d argument \n",ligne);exit(0);}
        if(s[posi1+4]!=')' && s[posi1+5]!=')'){printf("erreur ligne %d argument \n",ligne);exit(0);}
        else{
            if(s[posi1+4]==')')vi=posi1+4;
            else{vi=posi1+5;}
        }
        if(s[vi+1]!='#' && s[vi+1]!='r'){printf("erreur ligne %d argument\n",ligne);exit(0);}
        if(s[vi+2]=='\0'){printf("erreur ligne %d argument\n",ligne);exit(0);}
        
    }
    if(in>=10 && in<=11){
        if( strlen(s)<9 || strlen(s)>17 ){printf("erreur  ligne %d argument \n",ligne);exit(0);}
        int posia=0,i,cmpt=0,posi1=0;
        if( strlen(s)<9 || strlen(s)>17 ){printf("erreur  ligne %d argument \n",ligne);exit(0);}
        if(s[0]!='(' ||s[1]!='r'){printf("erreur  ligne %d argument \n",ligne);exit(0);}
        if(s[3]!=')' && s[4]!=')'){printf("erreur  ligne %d argument \n",ligne);exit(0);}
        if(s[3]==')')posia=3;
        else{posia=4;}
        if(s[posia+1]!='#' && s[posia+1]!='r'){printf("erreur ligne %d argument\n",ligne);exit(0);}
        for(i=0;s[i]!='\0';i++){// on chercher posi de 1 virgules si mal positione erreur
            if(s[i]==','){
                cmpt++;
                if(cmpt==1){posi1=i;}
                if(cmpt>1){printf("erreur  ligne %d argument \n",ligne);exit(0);}
            }
        }
        if(cmpt!=1){printf("erreur  ligne %d argument \n",ligne);exit(0);}
        if(s[posi1+1]!='r'){printf("erreur  ligne %d argument \n",ligne);exit(0);}
        if(s[posi1+2]=='\0'){printf("erreur  ligne %d argument \n",ligne);exit(0);}
    }
    // on verifie saut apres dans autre fonction
    if(in>=28 && in<=29){
        if( strlen(s)<2 || strlen(s)>3 ){printf("erreur ligne %d argument \n",ligne);exit(0);}
        if(s[0]!='r'){printf("erreur  ligne %d argument \n",ligne);exit(0);}
        if(s[1]=='\0'){printf("erreur  ligne %d argument \n",ligne);exit(0);}
    }
    if(in==31){
        if(s[0]!='\0'){
            printf("erreur ligne %d argument \n",ligne);exit(0);
        }
    }
}
// ICI
void remplir_T_memoire_verif_nombre(int ligne,int in,char *s,nb **T_memoire,char **base_instru){
    ligne--;
    strcpy(T_memoire[ligne*4]->instruction[0],base_instru[in]);
    //T_memoire[ligne*4]->test=1;
    if((in>=0 && in<=7) || in==30){
        char rd[15],rn[15],S[15];
        sscanf(s,"%[^,],%[^,],%s",rd,rn,S);
        convertir_numero_registre(rd,ligne);
        convertir_numero_registre(rn,ligne);
        if(S[0]=='r')convertir_numero_registre(S,ligne);
        if(S[0]=='#')convertir_nombre(S,ligne,0);
        strcpy(T_memoire[ligne*4]->instruction[1],rd);
        strcpy(T_memoire[ligne*4]->instruction[2],rn);
        strcpy(T_memoire[ligne*4]->instruction[3],S);
    }
    if(in>=8 && in<=11){
        char rd[15],rn[15],S[15];
        if(in>=8 && in<=9)sscanf(s,"%[^,],(%[^)])%s",rd,rn,S);
        if(in>=10 && in<=11)sscanf(s,"(%[^)])%[^,],%s",rd,S,rn);
        convertir_numero_registre(rd,ligne);
        convertir_numero_registre(rn,ligne);
        if(S[0]=='r')convertir_numero_registre(S,ligne);
        if(S[0]=='#')convertir_nombre(S,ligne,0);
        strcpy(T_memoire[ligne*4]->instruction[1],rd);
        strcpy(T_memoire[ligne*4]->instruction[2],rn);
        strcpy(T_memoire[ligne*4]->instruction[3],S);
        
    }
    if(in>=16 && in<=22){
        strcpy(T_memoire[ligne*4]->instruction[3],s);
    }
    if(in>=28 && in<=29){
        convertir_numero_registre(s,ligne);
        strcpy(T_memoire[ligne*4]->instruction[1],s);
    }
}

// ICI

// ON VERFIE SI ETIQUETTE EXISTENT BIEN SINON VALEUR VRAIE
void remplir_verif_etiq(nb **T_memoire,char **T_etiq,char **base_instru,int ligne1){
    int ligne,num_e,in,test=0;
    // d abord on verifie que chaque instru de saut a un bon parametre ie etiquette existe ou bon argument
    for(ligne=0;T_memoire[ligne]->instruction[0][0]!=' ';ligne=ligne+4){
        test=0;
        in=convertir_instru(T_memoire[ligne]->instruction[0],base_instru);
        if(in>=16 && in<=22){
            for(num_e=0;num_e<ligne1;num_e++){
                if(strcmp(" ",T_etiq[num_e])==0)continue;
                //printf("%s \n",T_etiq[num_e]);
                if(strcmp(T_memoire[ligne]->instruction[3],T_etiq[num_e])==0){sprintf(T_memoire[ligne]->instruction[3],"#%d",num_e*4);test=1;}//ici
            }
            if(test==0){
                if(T_memoire[ligne]->instruction[3][0]!='#' && T_memoire[ligne]->instruction[3][0]!='r'){
                    //printf("%s \n",T_memoire[ligne]->instruction[3]);
                    printf("erreur ligne %d parametre etiquette n existe pas\n",(ligne/4)+1);exit(0);}
                if(T_memoire[ligne]->instruction[3][0]=='#'){convertir_nombre(T_memoire[ligne]->instruction[3],(ligne/4),1);}
                if(T_memoire[ligne]->instruction[3][0]=='r')convertir_numero_registre(T_memoire[ligne]->instruction[3],(ligne/4));
            }
        }
    }
    
}

// ON PREND FICHIER D ENTREE ON VERIFIE SES PARAMETRES ET REMPLI NOTRE TABLEAU DE MEMOIRE AU PASSAGE
void verification_total_erreur_et_remplir_memoire(char **base_instru,nb **T_memoire,char nom_fichier[30]){
    int u=0,cmpt2=0;
    FILE *fichier=NULL;
    fichier=fopen(nom_fichier,"r");
    int test=-1,i,vide=0;
    char s[100];
    if(fichier !=NULL){
        char **T_etiq=creer_tab_etiq();
        int cmpt_ch=0,ligne=1,instru=-1;
        while(fscanf(fichier,"%s",s)!=EOF){
            vide=1;
            cmpt_ch++;
            // DEBUT LIGNE-------------------------------------------------------------------------------------------------------------------
            if(cmpt_ch==1){
                test=verifier_si_etiquette(s,base_instru,ligne,&u);// u contient emplacement du :
                if(test==1){
                    instru=apres(s,base_instru,ligne);
                    strcpy(T_etiq[ligne-1],s);// rempli etiquette suivant posi de son adresse
                    T_etiq[ligne-1][u]='\0';
                }
                if(test==2){// pas etiquette donc verifie si instru
                    instru=verif_si_instru(s,base_instru,ligne);
                }
            }
            //---------------------------------------------------------------------------------------------
            if(cmpt_ch>=1 && (test==1 ||test==2)){// tout ce qui vient apres  sont des argument
                i=0;
                char c=fgetc(fichier);
                while(c!=EOF && c!='\n'){
                    if(c!=' ')s[i++]=c;
                    if(i>17){printf("erreur ligne %d: trop de caractere\n",ligne);exit(0);}
                    c=fgetc(fichier);
                }
                s[i]='\0';
                fseek(fichier,-1,SEEK_CUR);
                verif_parametre(ligne,instru,s);// sans les nombres
                // si pas d erreur on rempli notre memoire en verifiant d abord les nombres
                remplir_T_memoire_verif_nombre(ligne,instru,s,T_memoire,base_instru);
            //---------------------------------------------------------------------------------------------
            }
            
            //---------------------------------------------------------------------------------------------
            if(test==0){
                if(cmpt_ch==1){strcpy(T_etiq[ligne-1],s);T_etiq[ligne-1][strlen(s)-1]='\0';}//rempli etiquette suivant posi de son adresse
                if(cmpt_ch==2) instru=verif_si_instru(s,base_instru,ligne);
                if(cmpt_ch>=2){// tout ce qui vient apres  sont des argument
                    i=0;
                    char c=fgetc(fichier);
                    while(c!=EOF && c!='\n'){
                        if(c!=' ')s[i++]=c;
                        if(i>17){printf("erreur ligne %d: trop de caractere\n",ligne);exit(0);}
                        c=fgetc(fichier);
                    }
                    s[i]='\0';
                    fseek(fichier,-1,SEEK_CUR);
                    verif_parametre(ligne,instru,s);// sans les nombres
                    // si pas d erreur on rempli notre memoire en verifiant d abord les nombres, on remplie ligne par ligne
                    remplir_T_memoire_verif_nombre(ligne,instru,s,T_memoire,base_instru);
            //---------------------------------------------------------------------------------------------
                }
            }
            // FIN DE LA LIGNE-------------------------------------------------------------
            if(fgetc(fichier)=='\n') {
                if(cmpt_ch<1 ||cmpt_ch>6 ){printf("\nerreur ecriture ligne : %d\nraison possible: oublie d'espace ou peu/pas d'arguments\n",ligne);exit(0);}
                if(test==0 && cmpt_ch==1){printf("\nerreur ligne: %d: peu d 'argument \n",ligne);exit(0);}
                cmpt2=cmpt_ch;
                cmpt_ch=0;
                ligne++;
                if(ligne>65535){printf("erreur :depassement taille memoire");exit(0);}
                
            }
        }
    // FIN LECTURE FICHIER-------------------------------------------------------------
        if(vide==0){printf("erreur :fichier vide\n");exit(0);}
        //ligne--; ??????
        if(cmpt2<1 ||cmpt2>6 ){printf("\nerreur ecriture ligne : %d raison possible: oublie d'espace ou peu/pas d'arguments\n",ligne);exit(0);}
        if(test==0 && cmpt_ch==1){printf("\nerreur ligne: %d: peu d 'argument \n",ligne);exit(0);}
        if(ligne>65536){printf("erreur :depassement taille memoire");exit(0);}
        remplir_verif_etiq(T_memoire,T_etiq,base_instru,ligne);
        liberer_tab_etiq(T_etiq);
        fclose(fichier);
    }
    else{printf("erreur d'ouverture du fichier source\n");fclose(fichier);exit(0);}
    
    
}
//FIN-----------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------


// FONCTIONS DE TRADUCTION

// DEBUT---------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------

char *rajouter_0(char ch[12]){
    char new[10]="0";
    int i=0;
    for(i=0;i<8-strlen(ch);i++){
        new[i]='0';
    }
    new[i]='\0';
    strcat(new,ch);
    strcpy(ch,new);
    char *new1=new;
    return new1;
}

int D(char h){
    char c='0';
    c=h;
    if(c-'0'>=0 && h-'0'<=9)return c-'0';
    if(c=='A' || c=='a')return 10;
    if(c=='B'|| c=='b')return 11;
    if(c=='C'|| c=='c')return 12;
    if(c=='D'|| c=='d')return 13;
    if(c=='E'|| c=='e')return 14;
    if(c=='F'|| c=='f')return 15;
    return -1;
}

void s_convertir(int T[],char *final){
    int v=T[0]<<27;
    int m=T[1]<<22;
    int s=T[2]<<17;
    int i=T[3]<<16;
    int o=T[4]<<0;
    int y=m+s+v+i+o;
    sprintf(final,"%X",y);
    rajouter_0(final);
    if(final==NULL || strlen(final)!=8){printf("erreur de conversion fichier hexadecimal\n");exit(0);}
    T[0]=16*D(final[6])+D(final[7]);//faible
    T[1]=16*D(final[4])+D(final[5]);
    T[2]=16*D(final[2])+D(final[3]);
    T[3]=16*D(final[0])+D(final[1]);//fort
}
//Cette fonction rempli aussi les valeurs du tableau de memoire par les octets des instructions
void generer_fichier_sortie(nb **T_memoire,char **base_instru){
    int j,i=0;
    int T[5];
    char *final=malloc(12);
    for(j=0;T_memoire[j]->instruction[0][0]!=' ';j++){}
    FILE *fichier=NULL;
    fichier=fopen("hexa.txt","w+");
    if(fichier !=NULL){
        for(j=0;T_memoire[j]->instruction[0][0]!=' ';j=j+4){
            
            // CODE OPERATOIRE
            T[0]=convertir_instru(T_memoire[j]->instruction[0],base_instru);
            
            // REGISTRE DESTINATION
            if(T_memoire[j]->instruction[1][0]!=' ')T[1]=convertir_numero_registre(T_memoire[j]->instruction[1],0);
            else{T[1]=0;}
            // SOURCE 1
            if(T_memoire[j]->instruction[2][0]!=' ')T[2]=convertir_numero_registre(T_memoire[j]->instruction[2],0);
            else{T[2]=0;}
            
            //BIT IMMEDIAT
            if(T_memoire[j]->instruction[3][0]==' '){T[3]=0;T[4]=0;}
            else{
                if(T_memoire[j]->instruction[3][0]=='r'){
                    T[3]=0;
                    T[4]=convertir_numero_registre(T_memoire[j]->instruction[3],0);
                }
                else{
                    T[3]=1;
                    T[4]=convertir_nombre(T_memoire[j]->instruction[3],0,0);
                }
            }
            s_convertir(T,final);
            fprintf(fichier,"%s\n",final);
            // ON remplie mnt les valeurs de notre memoire
            //T_memoire[i]->test=1;
            T_memoire[i++]->valeur=T[3];// adresse 0 <- octet de poids fort
            //T_memoire[i]->test=1;
            T_memoire[i++]->valeur=T[2];
            //T_memoire[i]->test=1;
            T_memoire[i++]->valeur=T[1];
            //T_memoire[i]->test=1;
            T_memoire[i++]->valeur=T[0];// adresse 3 <- octet de poids faible
            
        }
        fclose(fichier);
        free(final);
    }
    else{printf("impossible de generer fichier hexa.txt \n");}
}


// FIN---------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------

// FONCTIONS LIBERER ESPACE MEMOIRE

// DEBUT---------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------

 void liberer_base_instru(char **base_instru){
     int i;
     for(i=0;i<32;i++){
         free(base_instru[i]);
     }
     free(base_instru);
 }

void liberer_tab_registre(nb **tab_registre){
    int i;
    for(i=0;i<32;i++){
        free(tab_registre[i]);
    }
    free(tab_registre);
}
 


void liberer_tab_memoire(nb **tab_memoire){
    int i;
    for(i=0;i<65536;i++){
        free(tab_memoire[i]->instruction[3]);
        free(tab_memoire[i]->instruction[2]);
        free(tab_memoire[i]->instruction[1]);
        free(tab_memoire[i]->instruction[0]);
        free(tab_memoire[i]->instruction);
        free(tab_memoire[i]);
    }
    free(tab_memoire);
}

void liberer_tab_etiq(char **tab_etiq){
    int i;
    for(i=0;i<1000;i++){
        free(tab_etiq[i]);
    }
    free(tab_etiq);
}

// DEBUT---------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------
