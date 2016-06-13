import sys


def rep(name):
	fen = open(name+'.en')
	fes = open(name+'.es')
	lsen = []
	lses = []
	for l in fen:
		lw = l.split()
		lw.insert(0, "_NULL_")
		lsen.append(lw)
	for l in fes:
		lses.append(l.split())
	return lsen, lses

def rep2(name):
	fen = open(name+'.en')
	fes = open(name+'.es')
	lsen = []
	lses = []
	for l in fen:
		lsen.append(l.split())
	for l in fes:
		lses.append(l.split())
	return lsen, lses

def init_ts(lsen, lses):
	en2es = {}
	for index in range(0, len(lsen)):
		for ew in lsen[index]:
			en2es.setdefault(ew, {})
			for sw in lses[index]:
				en2es[ew][sw] = 1.0

	for ew in en2es.keys():
		sws = en2es[ew]
		for sw in sws.keys():
			sws[sw] = 1.0 / len(sws)
	return en2es

def run_it_one_time2(lsen, lses, en2es):
	cenes = {}
	cen = {}
	for index in range(0, len(lsen)):
		llen = lsen[index]
		lles = lses[index]
		for indexs in range(len(lles)):
			sw = lles[indexs]
			dived = 0.0
			for indexe in range(len(llen)):
				ew = llen[indexe]
				dived += en2es[ew][sw]
			for indexe in range(len(llen)):
				ew = llen[indexe]
				sigma = (en2es[ew][sw] )/dived
				cenes.setdefault(ew, {})
				cenes[ew].setdefault(sw, 0.0)
				cen.setdefault(ew, 0.0)
				cenes[ew][sw] += sigma
				cen[ew] += sigma

	for ew in cenes.keys():
		for sw in cenes[ew].keys():
			cenes[ew][sw] /= cen[ew]
	return cenes

def run_it_one_time(lsen, lses, en2es, qs):
	cenes = {}
	cen = {}
	cqs = {}
	cdqs = {}
	for index in range(0, len(lsen)):
		llen = lsen[index]
		lles = lses[index]
		lenen = len(llen)
		lenes = len(lles)
		cqs.setdefault((lenen, lenes), {})
		cdqs.setdefault((lenen, lenes), {})
		qqs = cqs[(lenen, lenes)]
		dqs = cdqs[(lenen, lenes)]
		mqs = qs[(lenen, lenes)]
		for indexs in range(len(lles)):
			sw = lles[indexs]
			dived = 0.0
			for indexe in range(len(llen)):
				ew = llen[indexe]
				dived += en2es[ew][sw] * mqs[(indexe, indexs)]
			for indexe in range(len(llen)):
				ew = llen[indexe]
				sigma = (en2es[ew][sw] * mqs[(indexe, indexs)])/dived
				cenes.setdefault(ew, {})
				cenes[ew].setdefault(sw, 0.0)
				cen.setdefault(ew, 0.0)
				cenes[ew][sw] += sigma
				cen[ew] += sigma
				qqs.setdefault((indexe, indexs), 0.0)
				qqs[(indexe, indexs)] += sigma
				dqs.setdefault(indexs, 0.0)
				dqs[indexs] += sigma

	for ew in cenes.keys():
		for sw in cenes[ew].keys():
			cenes[ew][sw] /= cen[ew]
	for ml in cqs.keys():
		qqs = cqs[ml]
		dqs = cdqs[ml]
		for ij in qqs.keys():
			qqs[ij] /= dqs[ij[1]]
	return cenes, cqs

def init_qs(lsen, lses):
	qs = {}

	for index in range(0, len(lsen)):
		qs[(len(lsen[index]), len(lses[index]))] = {}

	for a in qs.keys():
		le = a[0]
		ls = a[1]
		m = qs[a]
		for i in range(le):
			for j in range (ls):
				m[(i, j)] = 1.0/le
	return qs

def train_ts1(name):
	lsen, lses = rep(name)
	en2es = init_ts(lsen, lses)
	for index in range(5):
		en2es = run_it_one_time2(lsen, lses, en2es)
	qs = init_qs(lsen, lses)
	for index in range(5):
		en2es, qs = run_it_one_time(lsen, lses, en2es, qs)
		print >>sys.stderr, "one round!"
	lsen, lses = rep2("test")
	aligin(lsen, lses, en2es, qs)

def aligin(lsen, lses, en2es, qs):
	for i in range(len(lsen)):
		llen = lsen[i]
		lles = lses[i]
		lenen = len(llen)
		lenes = len(lles)
		for iz in range(len(lles)):
			maxi = 0
			maxt = 0.0
			for ie in range(len(llen)):
				compare = en2es[llen[ie]][lles[iz]] * qs[(lenen+1, lenes)][(ie+1, iz)]
				if compare > maxt:
					maxt = compare
					maxi = ie
			if maxt > en2es["_NULL_"][lles[iz]] * qs[(lenen+1, lenes)][(0, iz)]:
				print i+1, maxi+1, iz+1

train_ts1(sys.argv[1]) 





