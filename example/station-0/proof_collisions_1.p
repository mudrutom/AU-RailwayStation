include('order.p').
include('layout.p').
include('control.p').

% ADDITIONAL AXIOMS :

% the train always enters for in1
fof(alwaysEnters_in1, axiom, (
   (![X]: ![T]: enter(X, T, in1))
)).
% the train driver always goes
fof(alwaysGoes, axiom, (
   (![X]: ![T]: goes(X, T))
)).

% no train collisions for v1
fof(noCollisions_v1, conjecture, (
   (![X]: ![T1]: ![T2]: (((T1 != T2) & at(X, T1, v1)) => ~at(X, T2, v1)))
)).