// Agent sample_agent in project traffic

/* Initial beliefs and rules */

/* Initial goals */


/* Plans */


+quemEstaAi : true
	<-
	.print("Estou ativo no cenário").
	
+oi : true
	<- 
	   .print("recebi um oi e vou mandar um ola");
	   ola.