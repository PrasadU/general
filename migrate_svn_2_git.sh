SCRIPT_DIR=$(basdir ${BASH_SOURCE})
. ${SCRIPT_DIR/git-info.sh}

MIG_DIR=migration
PRJ_SVN_NAME=prj-name
PRJ_SVN_URL=
PRJ_GIT_NAME=
PRJ_GIT_URL=

mkdir ${MIG_DIR}
pushd ${MIG_DIR}
echo -e "running in $PWD --- ${MIG_DIR} \n ${PRJ_SVN_NAME} ${PRJ_SVN_URL} \n ${PRJ_GIT_NAME} ${PRJ_GIT_URL}"

# Step-1 : Get User List
svn log -q ${PRJ_SVN_URL} > remote-authors.txt
cat remote-authors.txt | awk -F '|' '/^r/ {sub("^ ", "", $2); sub(" $", "", $2); print $2" = "$2" <"$2">"}' | sort -u > authors.txt

# Step-2 : Get SVN Repo
git svn clone --authors-file=authors.txt --no-metsdata --prefix "" -s {PRJ_SVN_URL} ${PRJ_GIT_NAME}
listAll > initial.log

# Step-3 : Clean-up
for t in $(git for-each-ref --format='%(refname:short)' refs/remotes/tags); do git tag ${t/tags\//} $t && git branch -D -r $t; done
for b in $(git for-each-ref --format='%(refname:short)' refs/remotes); do git branch $b refs/remotes/$b && git branch -D -r $b; done
for p in $(git for-each-ref --format='%(refname:short)' | grep @); do git branch -D $p; done

listAll > initial.log

# Step-4 : Push to GIT RREMOTE EPO 
echo "check logs ${MIG_DIR} - if happy - push to git remote"
echo -----------------------
echo git remote add origin ${PRJ_GIT_URL}
echo git push origin --all
echo git push origin --tags
echo -----------------------

popd 