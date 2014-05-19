include('order.p').
include('layout.p').
include('control.p').

% ADDITIONAL AXIOMS :

% the train always enters for in1
fof(alwaysEnters_in1, axiom, (
   (![X]: ![T]: enter(X, T, in1))
)).
% the train always enters for in2
fof(alwaysEnters_in2, axiom, (
   (![X]: ![T]: enter(X, T, in2))
)).

% no train derails for v2
fof(noDerails_v2, conjecture, (
   (![X]: ![T]: ((at(X, T, v2) & at(succ(X), T, v2)) => (switch(X, v2) = switch(succ(X), v2))))
)).
