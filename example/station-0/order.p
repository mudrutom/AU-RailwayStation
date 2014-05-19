fof(antisymmetry, axiom, (
   (![X]: ![Y]: ((less(X, Y) & less(Y, X)) => (X = Y)))
)).
fof(transitivity, axiom, (
   (![X]: ![Y]: ![Z]: ((less(X, Y) & less(Y, Z)) => less(X, Y)))
)).
fof(totality, axiom, (
   (![X]: ![Y]: (less(X, Y) | less(Y, X)))
)).
fof(successor, axiom, (
   (![X]: (less(X, succ(X)) & (![Y]: (less(Y, X) | less(succ(X), Y)))))
)).
fof(predecessor, axiom, (
   (![X]: ((pred(succ(X)) = X) & (succ(pred(X)) = X)))
)).
