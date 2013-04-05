package feCapes.asm;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

import java.util.HashMap;
import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.relauncher.IClassTransformer;

public class FeCapesTransformer implements IClassTransformer
{
	HashMap<String, String> deobf = deobf();
	HashMap<String, String> obf = obf();
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) 
	{
		if (name.equalsIgnoreCase(deobf.get("className1")) || name.equalsIgnoreCase(deobf.get("className2")))
			return transform_do(name, bytes, deobf);
		else if (name.equalsIgnoreCase(obf.get("className1")) || name.equalsIgnoreCase(obf.get("className2")))
			return transform_do(name, bytes, obf);
		else
			return bytes;
	}
	
	private HashMap<String, String> deobf()
	{
		HashMap<String, String> data = new HashMap<String, String>();
		
		data.put("className1", "net.minecraft.client.entity.EntityPlayerSP");
		data.put("className2", "net.minecraft.client.entity.EntityOtherPlayerMP");
		
		data.put("targetMethodName", "updateCloak");
		
		data.put("entityPlayerJavaClassName", "net/minecraft/entity/player/EntityPlayer");
		
		return data;
	}
	
	private HashMap<String, String> obf() 
	{
		HashMap<String, String> data = new HashMap<String, String>();
		
		//TODO well, this.
		
		return data;
	}
	
	private byte[] transform_do(String name, byte[] bytes, HashMap<String, String> hm)
	{
		System.out.println("[FeCapes] Transforming " + name + "...");
		
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		Iterator<MethodNode> methods = classNode.methods.iterator();

		while (methods.hasNext())
		{
			MethodNode m = methods.next();
			
			if (m.name.equals(hm.get("targetMethodName")))
			{
				System.out.println("[FeCapes] Found the right method.");
				
				int offset = 0;
				while (m.instructions.get(offset).getOpcode() != Opcodes.RETURN)
				{
					offset++;
				}
				
				LabelNode lblNode = new LabelNode(new Label());
				InsnList toInject = new InsnList();
				toInject.add(new VarInsnNode(ALOAD, 0));
				toInject.add(new MethodInsnNode(INVOKESTATIC, "feCapes/CapeHandler", "callHook", "(L" + hm.get("entityPlayerJavaClassName") + ";)V"));
				toInject.add(lblNode);
				
				m.instructions.insertBefore(m.instructions.get(offset), toInject);
				
				System.out.println("[FeCapes] CapeHandler method added.");
				break;
			}
		}
		
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		classNode.accept(writer);
		return writer.toByteArray();
	}	
}
