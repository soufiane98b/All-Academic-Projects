/**************************************/
/*            minishell.c             */
/**************************************/

#include<unistd.h>
#include<stdio.h>
#include<sys/wait.h>
#include<sys/stat.h>
#include<fcntl.h>
#include<sys/types.h>
#include<stdlib.h>
#include<string.h>
#include<errno.h>
#include "analex.h"

#define TAILLE_MAX 100 /* taille max d'un mot */
#define ARGS_MAX 10    /* nombre maximum d'arguments a une commande */

/* Execute la commande donnee par le tableau argv[] dans un nouveau processus ;
 * les deux premiers parametres indiquent l'entree et la sortie de la commande,
 * a rediriger eventuellement.
 * Renvoie le numero du processus executant la commande
 *
 * FONCTION À MODIFIER/COMPLÉTER.
 *
*/
pid_t execute(int entree, int sortie, char* argv[]) {
    pid_t p_fils=fork();
    if(p_fils==0){// on est dans le processus fils
            
        if(sortie!=1){
            dup2(sortie,1);// on redirige
            close(sortie);
        }
        if(entree!=0){
            dup2(entree,0); // on ridirige
            close(entree);
        }
        execvp(argv[0],argv);
        printf("commande introuvable \n");
        exit(0);
    }
    else{// on est dans le pere
        //wait est dans le main pour pouvoir gerer les &
        return p_fils;
    }

  return 0; 
}

/* Lit et execute une commande en recuperant ses tokens ;
 * les deux premiers parametres indiquent l'entree et la sortie de la commande ;
 * pid contient le numero du processus executant la commande et
 * background est non nul si la commande est lancee en arriere-plan
 *
 * FONCTION À MODIFIER/COMPLÉTER.
*/
TOKEN commande(int entree, int sortie, pid_t* pid, int* background) {
  TOKEN t;
  char word[TAILLE_MAX];
    // on recupure commande par commande, fin commande = NL ou SEMI
    char *tabArgs[ARGS_MAX]={NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL};//ARGS_MAX==10
    int cmpt_arg=0;
    while(1){
        t = getToken(word);
        //----------------------------------------------------------------
        if(cmpt_arg>9){printf("trop d'argument dans la commande");exit(0);}
        if(t==T_WORD){
            tabArgs[cmpt_arg]=malloc(strlen(word)*sizeof(char));
            strcpy(tabArgs[cmpt_arg],word);
        }
        if(t==T_GT){// AVEC TRONCATURE
            getToken(word); // on redirige sortie dans le fichier contenu dans word
            remove(word);
            sortie=open(word,O_WRONLY|O_CREAT,0640);
            if(sortie<0){printf("erreur ouverture fichier de sortie ");exit(0);}
        }
        if(t==T_GTGT){// SANS TRONCATURE
            getToken(word); // on redirige sortie dans le fichier contenu dans word
            sortie=open(word,O_RDWR|O_CREAT,0640);
            if(sortie<0){printf("erreur ouverture fichier de sortie ");exit(0);}
            char buff[100];
            while(read(sortie,buff,100)>0);// on ecrit fin fichier
        }
        if(t==T_LT){
            getToken(word); // on redirige entree dans le fichier contenu dans word
            entree=open(word,O_RDWR,0640);
            if(entree<0){printf("erreur ouverture fichier de sortie ");exit(0);}
        }
        if(t==T_BAR){
            int fd_pipe[2];
            pipe(fd_pipe);
            *pid=execute(0,fd_pipe[1],tabArgs);
            close(fd_pipe[1]);// TRES TRES IMPORTANT
            wait(NULL);// car on a mis le wait dans le main donc faut le mettre ici sinon on le fait jamais et donc processus zombie ...
            t=commande(fd_pipe[0],1,pid,background);
            return t;
        }
        
        if(t==T_AMPER){
            *background=1;
        }
        if(t==T_SEMI){
            *pid=execute(entree,sortie,tabArgs);
            return T_SEMI;
        }
        
        if(t==T_NL){
            *pid=execute(entree,sortie,tabArgs);
            return T_NL;
        }
        if(t==T_EOF){
            return T_EOF;
        }
        
        if(t!=T_SEMI)cmpt_arg++;
        //----------------------------------------------------------------
    }
  return t; 
}

/* Retourne une valeur non-nulle si minishell est en train de s'exécuter en mode interactif, 
 * c'est à dire, si l'affichage de minishell n'est pas redirigé vers un fichier.
 * (Important pour les tests automatisés.)
 *
 * NE PAS MODIFIER CETTE FONCTION.
 */
int is_interactive_shell(){
  return isatty(1);
}

/* Affiche le prompt "minishell>" uniquement si l'affichage n'est pas redirigé vers un fichier. 
 * (Important pour les tests automatisés.)
 *
 * NE PAS MODIFIER CETTE FONCTION.
 */
void print_prompt(){
  if(is_interactive_shell()){ 
    printf("mini-shell>");
    fflush(stdout);
  }  
}

/* Fonction main 
 * FONCTION À MODIFIER/COMPLÉTER.
 */
int main(int argc, char* argv[]) {
	TOKEN t;
	pid_t pid, fid;
	int background = 0;
	int status = 0;
    int test=-20;

	print_prompt(); // affiche le prompt "minishell>" 
	
	while ( (t = commande(0, 1, &pid, &background)) != T_EOF) {
        
	  if (t == T_NL || t==T_SEMI) {
          if( background==0 ) {
              while((test=wait(NULL))!=pid) printf("[%d] fin arriere plan \n[%d] status\n",test,WEXITSTATUS(status));
          }
            if(background!=0 ){
                background=0;
                fid=pid;
                printf("[%d] lancement arriere plan \n",fid);
          }
          if(t==T_NL)print_prompt();
                
          
      }
    }
	if(is_interactive_shell())
	  printf("\n") ;

	return status; // Attention à la valeur de retour du wait, voir man wait et la macro WEXITSTATUS
}
