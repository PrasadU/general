func listAll {
	echo -e "\n git-tags \n-------------------\n$(git tag -l)\n-------------------"
	echo -e "\n git-branch \n-------------------\n$(git branch -l)\n-------------------"
	echo -e "\n git-branch-remotes \n-------------------\n$(git branch -lr)\n-------------------"
	echo -e "\n git-tags-2 \n-------------------\n$(git for-each-ref --format='%(refname:short)' refs/remotes/tags)\n-------------------"
	echo -e "\n git-branch-2 \n-------------------\n$(git for-each-ref --format='%(refname:short)' refs/remotes)\n-------------------"
	echo -e "\n git-@ \n-------------------\n$(git for-each-ref --format='%(refname:short)' | grep @)\n-------------------"
	return 0
}

listAll