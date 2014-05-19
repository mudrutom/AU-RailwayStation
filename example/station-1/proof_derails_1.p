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

% no train derails for v1
fof(noDerails_v1, conjecture, (
   (![X]: ![T]: ((at(X, T, v1) & at(succ(X), T, v1)) => (switch(X, v1) = switch(succ(X), v1))))
)).
