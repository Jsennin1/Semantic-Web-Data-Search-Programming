/ / p a c k a g e   j p . k a n e i w a . k a d a i 1 ;  
  
 i m p o r t   j a v a . i o . B u f f e r e d R e a d e r ;  
  
 i m p o r t   j a v a . i o . F i l e I n p u t S t r e a m ;  
 i m p o r t   j a v a . i o . I O E x c e p t i o n ;  
 i m p o r t   j a v a . i o . I n p u t S t r e a m R e a d e r ;  
 i m p o r t   j a v a . u t i l . A r r a y L i s t ;  
 i m p o r t   j a v a . u t i l . H a s h S e t ;  
 i m p o r t   j a v a . u t i l . L i s t ;  
 i m p o r t   j a v a . u t i l . S e t ;  
 i m p o r t   j a v a . i o . * ;    
  
  
 p u b l i c   c l a s s   L o a d C S V   {  
 	  
 	     s t a t i c   S t r i n g   p a t h T o C s v =   " s r c / a . c s v " ;  
 	         s t a t i c   A r r a y L i s t < S t r i n g >   u r i ;            
 	         s t a t i c   S e t < L i s t < I n t e g e r > >   i d T r i p l e ;  
  
 	           s t a t i c   v o i d   R e a d C S V ( ) {  
 	                 u r i   =   n e w   A r r a y L i s t < S t r i n g > ( ) ;  
 	                 i d T r i p l e   =   n e w   H a s h S e t < L i s t < I n t e g e r > > ( ) ;  
 	                 t r y   ( B u f f e r e d R e a d e r   c s v R e a d e r   =   n e w   B u f f e r e d R e a d e r ( n e w   F i l e R e a d e r ( p a t h T o C s v ) ) ) {  
 	                          
 	                         S t r i n g   r o w ;  
 	                         w h i l e   ( ( r o w   =   c s v R e a d e r . r e a d L i n e ( ) )   ! =   n u l l )   {  
 	                                 S t r i n g [ ]   d a t a   =   r o w . s p l i t ( " , " ) ;  
 	                                 A r r a y L i s t < I n t e g e r >   i d   =   n e w   A r r a y L i s t < I n t e g e r > ( ) ;  
 	                                 f o r   ( S t r i n g   w o r d   :   d a t a )   {  
 	                                         i f ( ! u r i . c o n t a i n s ( w o r d ) ) {  
 	                                         i d . a d d ( u r i . s i z e ( ) ) ;  
 	                                         u r i . a d d ( w o r d ) ;  
 	                                         }  
 	                                         e l s e {  
 	                                                 i d . a d d ( u r i . i n d e x O f ( w o r d ) ) ;  
 	                                         }  
 	                                 }        
 	                                 i d T r i p l e . a d d ( i d ) ;  
 	                         }  
 	                         c s v R e a d e r . c l o s e ( ) ;  
 	                 } c a t c h   ( E x c e p t i o n   e )   {  
 	                         S y s t e m . o u t . p r i n t l n ( e + "   f i l e   i s   n o t   f o u n d " ) ;  
 	                 }  
 	                  
 	         }  
 	          
 	         s t a t i c   v o i d   p r i n t U R I a n d I d T r i p l e ( ) {  
 	                 S y s t e m . o u t . p r i n t l n ( " u r i : " ) ;  
 	                  
 	                 f o r   ( i n t   x   =   0 ;   x < u r i . s i z e ( ) ;   x + + ) {  
 	                         S y s t e m . o u t . p r i n t l n ( " u r i [ " + x + " ] = "   +   u r i . g e t ( x ) ) ;  
 	                 }  
 	                 S y s t e m . o u t . p r i n t l n ( " i d T r i p l e : " ) ;  
  
 	                 f o r   ( L i s t < I n t e g e r >   l i s t   :   i d T r i p l e )   {  
 	                         S y s t e m . o u t . p r i n t l n ( l i s t ) ;  
 	                 }  
 	         }  
  
 	          
 	 p u b l i c   s t a t i c   v o i d   m a i n ( S t r i n g   a r g s [ ] ) {  
 	         R e a d C S V ( ) ;  
 	         p r i n t U R I a n d I d T r i p l e ( ) ;  
 	 }  
 }  
 