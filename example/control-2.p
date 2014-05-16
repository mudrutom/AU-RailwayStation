% switch configuration axiom for (in2->out3)#0
fof(conf_in2_out3_0, axiom, (
   (![X]: (conf(X, in2_out3_0) <=> ((switch(X, s4) = s3) & (switch(X, s3) = out3))))
)).
% free path axiom for (in2->out3)#0
fof(free_in2_out3_0, axiom, (
   (![X]: ![T]: (free(X, in2_out3_0) <=> (~at(X, T, s4) & ~at(X, T, s3) & ~at(X, T, out3) & ~at(X, T, s2) & ~at(X, T, s7) & ~at(X, T, s5))))
)).

% switch configuration axiom for (in2->out2)#1
fof(conf_in2_out2_1, axiom, (
   (![X]: (conf(X, in2_out2_1) <=> ((switch(X, s4) = out2))))
)).
% free path axiom for (in2->out2)#1
fof(free_in2_out2_1, axiom, (
   (![X]: ![T]: (free(X, in2_out2_1) <=> (~at(X, T, s4) & ~at(X, T, out2) & ~at(X, T, s3) & ~at(X, T, s2) & ~at(X, T, s7) & ~at(X, T, s5))))
)).

% switch configuration axiom for (in2->out2)#0
fof(conf_in2_out2_0, axiom, (
   (![X]: (conf(X, in2_out2_0) <=> ((switch(X, s4) = s3) & (switch(X, s3) = out2))))
)).
% free path axiom for (in2->out2)#0
fof(free_in2_out2_0, axiom, (
   (![X]: ![T]: (free(X, in2_out2_0) <=> (~at(X, T, s4) & ~at(X, T, s3) & ~at(X, T, out2) & ~at(X, T, s2) & ~at(X, T, s7) & ~at(X, T, s5))))
)).

% switch configuration axiom for (in2->out1)#1
fof(conf_in2_out1_1, axiom, (
   (![X]: (conf(X, in2_out1_1) <=> ((switch(X, s4) = s2))))
)).
% free path axiom for (in2->out1)#1
fof(free_in2_out1_1, axiom, (
   (![X]: ![T]: (free(X, in2_out1_1) <=> (~at(X, T, s4) & ~at(X, T, s2) & ~at(X, T, out1) & ~at(X, T, s3) & ~at(X, T, s7) & ~at(X, T, s5))))
)).

% switch configuration axiom for (in2->out1)#0
fof(conf_in2_out1_0, axiom, (
   (![X]: (conf(X, in2_out1_0) <=> ((switch(X, s4) = s3) & (switch(X, s3) = out1))))
)).
% free path axiom for (in2->out1)#0
fof(free_in2_out1_0, axiom, (
   (![X]: ![T]: (free(X, in2_out1_0) <=> (~at(X, T, s4) & ~at(X, T, s3) & ~at(X, T, out1) & ~at(X, T, s2) & ~at(X, T, s7) & ~at(X, T, s5))))
)).

% switch configuration axiom for (in1->out3)#0
fof(conf_in1_out3_0, axiom, (
   (![X]: (conf(X, in1_out3_0) <=> ((switch(X, s7) = s3) & (switch(X, s3) = out3))))
)).
% free path axiom for (in1->out3)#0
fof(free_in1_out3_0, axiom, (
   (![X]: ![T]: (free(X, in1_out3_0) <=> (~at(X, T, s7) & ~at(X, T, s3) & ~at(X, T, out3) & ~at(X, T, s4) & ~at(X, T, s2) & ~at(X, T, s5))))
)).

% switch configuration axiom for (in1->out2)#0
fof(conf_in1_out2_0, axiom, (
   (![X]: (conf(X, in1_out2_0) <=> ((switch(X, s7) = s3) & (switch(X, s3) = out2))))
)).
% free path axiom for (in1->out2)#0
fof(free_in1_out2_0, axiom, (
   (![X]: ![T]: (free(X, in1_out2_0) <=> (~at(X, T, s7) & ~at(X, T, s3) & ~at(X, T, out2) & ~at(X, T, s4) & ~at(X, T, s2) & ~at(X, T, s5))))
)).

% switch configuration axiom for (in1->out1)#1
fof(conf_in1_out1_1, axiom, (
   (![X]: (conf(X, in1_out1_1) <=> ((switch(X, s7) = s3) & (switch(X, s3) = out1))))
)).
% free path axiom for (in1->out1)#1
fof(free_in1_out1_1, axiom, (
   (![X]: ![T]: (free(X, in1_out1_1) <=> (~at(X, T, s7) & ~at(X, T, s3) & ~at(X, T, out1) & ~at(X, T, s4) & ~at(X, T, s2) & ~at(X, T, s5))))
)).

% switch configuration axiom for (in1->out1)#0
fof(conf_in1_out1_0, axiom, (
   (![X]: (conf(X, in1_out1_0) <=> ((switch(X, s7) = s2))))
)).
% free path axiom for (in1->out1)#0
fof(free_in1_out1_0, axiom, (
   (![X]: ![T]: (free(X, in1_out1_0) <=> (~at(X, T, s7) & ~at(X, T, s2) & ~at(X, T, out1) & ~at(X, T, s4) & ~at(X, T, s3) & ~at(X, T, s5))))
)).

% switch configuration axiom for (in3->out3)#0
fof(conf_in3_out3_0, axiom, (
   (![X]: (conf(X, in3_out3_0) <=> ((switch(X, s5) = s3) & (switch(X, s3) = out3))))
)).
% free path axiom for (in3->out3)#0
fof(free_in3_out3_0, axiom, (
   (![X]: ![T]: (free(X, in3_out3_0) <=> (~at(X, T, s5) & ~at(X, T, s3) & ~at(X, T, out3) & ~at(X, T, s4) & ~at(X, T, s7) & ~at(X, T, s2))))
)).

% switch configuration axiom for (in3->out2)#1
fof(conf_in3_out2_1, axiom, (
   (![X]: (conf(X, in3_out2_1) <=> ((switch(X, s5) = out2))))
)).
% free path axiom for (in3->out2)#1
fof(free_in3_out2_1, axiom, (
   (![X]: ![T]: (free(X, in3_out2_1) <=> (~at(X, T, s5) & ~at(X, T, out2) & ~at(X, T, s4) & ~at(X, T, s3) & ~at(X, T, s7) & ~at(X, T, s2))))
)).

% switch configuration axiom for (in3->out2)#0
fof(conf_in3_out2_0, axiom, (
   (![X]: (conf(X, in3_out2_0) <=> ((switch(X, s5) = s3) & (switch(X, s3) = out2))))
)).
% free path axiom for (in3->out2)#0
fof(free_in3_out2_0, axiom, (
   (![X]: ![T]: (free(X, in3_out2_0) <=> (~at(X, T, s5) & ~at(X, T, s3) & ~at(X, T, out2) & ~at(X, T, s4) & ~at(X, T, s7) & ~at(X, T, s2))))
)).

% switch configuration axiom for (in3->out1)#1
fof(conf_in3_out1_1, axiom, (
   (![X]: (conf(X, in3_out1_1) <=> ((switch(X, s5) = s2))))
)).
% free path axiom for (in3->out1)#1
fof(free_in3_out1_1, axiom, (
   (![X]: ![T]: (free(X, in3_out1_1) <=> (~at(X, T, s5) & ~at(X, T, s2) & ~at(X, T, out1) & ~at(X, T, s4) & ~at(X, T, s3) & ~at(X, T, s7))))
)).

% switch configuration axiom for (in3->out1)#0
fof(conf_in3_out1_0, axiom, (
   (![X]: (conf(X, in3_out1_0) <=> ((switch(X, s5) = s3) & (switch(X, s3) = out1))))
)).
% free path axiom for (in3->out1)#0
fof(free_in3_out1_0, axiom, (
   (![X]: ![T]: (free(X, in3_out1_0) <=> (~at(X, T, s5) & ~at(X, T, s3) & ~at(X, T, out1) & ~at(X, T, s4) & ~at(X, T, s2) & ~at(X, T, s7))))
)).

% open the signal in1 for a path to out1
fof(open_in1_out1, axiom, (
   (![X]: ![T]: (((clock(X) = in1) & at(X, T, in1) & (gate(T) = out1) & ((conf(X, in1_out1_1) & free(X, in1_out1_1)) | (conf(X, in1_out1_0) & free(X, in1_out1_0)))) => open(X, in1)))
)).
% open the signal in1 for a path to out2
fof(open_in1_out2, axiom, (
   (![X]: ![T]: (((clock(X) = in1) & at(X, T, in1) & (gate(T) = out2) & ((conf(X, in1_out2_0) & free(X, in1_out2_0)))) => open(X, in1)))
)).
% open the signal in1 for a path to out3
fof(open_in1_out3, axiom, (
   (![X]: ![T]: (((clock(X) = in1) & at(X, T, in1) & (gate(T) = out3) & ((conf(X, in1_out3_0) & free(X, in1_out3_0)))) => open(X, in1)))
)).
% close the signal in1 when a train leaves
fof(close_in1, axiom, (
   (![X]: ![T]: ((at(pred(X), T, in1) & open(X, in1) & ~at(X, T, in1)) => ~open(X, in1)))
)).

% open the signal in2 for a path to out1
fof(open_in2_out1, axiom, (
   (![X]: ![T]: (((clock(X) = in2) & at(X, T, in2) & (gate(T) = out1) & ((conf(X, in2_out1_1) & free(X, in2_out1_1)) | (conf(X, in2_out1_0) & free(X, in2_out1_0)))) => open(X, in2)))
)).
% open the signal in2 for a path to out2
fof(open_in2_out2, axiom, (
   (![X]: ![T]: (((clock(X) = in2) & at(X, T, in2) & (gate(T) = out2) & ((conf(X, in2_out2_1) & free(X, in2_out2_1)) | (conf(X, in2_out2_0) & free(X, in2_out2_0)))) => open(X, in2)))
)).
% open the signal in2 for a path to out3
fof(open_in2_out3, axiom, (
   (![X]: ![T]: (((clock(X) = in2) & at(X, T, in2) & (gate(T) = out3) & ((conf(X, in2_out3_0) & free(X, in2_out3_0)))) => open(X, in2)))
)).
% close the signal in2 when a train leaves
fof(close_in2, axiom, (
   (![X]: ![T]: ((at(pred(X), T, in2) & open(X, in2) & ~at(X, T, in2)) => ~open(X, in2)))
)).

% open the signal in3 for a path to out1
fof(open_in3_out1, axiom, (
   (![X]: ![T]: (((clock(X) = in3) & at(X, T, in3) & (gate(T) = out1) & ((conf(X, in3_out1_1) & free(X, in3_out1_1)) | (conf(X, in3_out1_0) & free(X, in3_out1_0)))) => open(X, in3)))
)).
% open the signal in3 for a path to out2
fof(open_in3_out2, axiom, (
   (![X]: ![T]: (((clock(X) = in3) & at(X, T, in3) & (gate(T) = out2) & ((conf(X, in3_out2_1) & free(X, in3_out2_1)) | (conf(X, in3_out2_0) & free(X, in3_out2_0)))) => open(X, in3)))
)).
% open the signal in3 for a path to out3
fof(open_in3_out3, axiom, (
   (![X]: ![T]: (((clock(X) = in3) & at(X, T, in3) & (gate(T) = out3) & ((conf(X, in3_out3_0) & free(X, in3_out3_0)))) => open(X, in3)))
)).
% close the signal in3 when a train leaves
fof(close_in3, axiom, (
   (![X]: ![T]: ((at(pred(X), T, in3) & open(X, in3) & ~at(X, T, in3)) => ~open(X, in3)))
)).

% the control clock has to be in one of the input nodes
fof(clockOptions, axiom, (
   (![X]: ((clock(X) = in1) | (clock(X) = in2) | (clock(X) = in3)))
)).
% the sequence of tics of the control clock
fof(clockTic, axiom, (
   (![X]: ((clock(X) = in3) <=> (clock(succ(X)) = in1)))
)).
fof(clockTic_0, axiom, (
   (![X]: ((clock(X) = in1) <=> (clock(succ(X)) = in2)))
)).
fof(clockTic_1, axiom, (
   (![X]: ((clock(X) = in2) <=> (clock(succ(X)) = in3)))
)).
